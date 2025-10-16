package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentSave;
import com.sprint.mission.discodeit.dto.Message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.Message.MessageDto;
import com.sprint.mission.discodeit.dto.Page.PageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentStorage binaryContentStorage;
    private final PageResponseMapper pageResponseMapper;

    @Override
    public MessageDto create(List<MultipartFile> multipartFiles, MessageCreateRequest messageCreateRequest) {

        User user = userRepository.findById(messageCreateRequest.authorId())
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + messageCreateRequest.authorId() + " not found"));

        Channel channel = channelRepository.findById(messageCreateRequest.channelId())
                .orElseThrow(() -> new IllegalArgumentException("Channel with id: " + messageCreateRequest.channelId() + " not found"));

        if (multipartFiles != null && !multipartFiles.get(0).isEmpty()) {
            List<BinaryContentSave> messageBinaryContents = getBinaryContents(multipartFiles);
            List<BinaryContent> binaryContents = new ArrayList<>();

            for (BinaryContentSave binaryContentSave : messageBinaryContents) {
                binaryContents.add(binaryContentSave.binaryContent());
                binaryContentStorage.put(binaryContentSave.binaryContent().getId(), binaryContentSave.data());
            }

            binaryContentRepository.saveAll(binaryContents);
            Message message = new Message(user, channel, messageCreateRequest.content(), binaryContents);
            messageRepository.save(message);

            return MessageMapper.INSTANCE.toDto(message);
        }

        Message message = new Message(user, channel, messageCreateRequest.content());
        messageRepository.save(message);

        return MessageMapper.INSTANCE.toDto(message);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MessageDto> findAllByChannelId(UUID channelId, Instant cursor, Pageable pageable) {
        Slice<Message> slice;
        if (cursor == null) {
            slice = messageRepository.findByChannelIdOrderByCreatedAtDesc(channelId, pageable);
        }else{
            slice = messageRepository.findByCourseIdAndIdLessThanOrderByIdDesc(channelId, cursor, pageable);
        }
        System.out.println(slice.toString());

        Slice<MessageDto> messageDtoSlice = slice.map(MessageMapper.INSTANCE::toDto);
        return pageResponseMapper.fromSlice(messageDtoSlice);
    }

    @Override
    public MessageDto update(UUID messageId, String newContent) {
        Message messageUpdated = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        messageUpdated.update(newContent);
        messageRepository.save(messageUpdated);

        return MessageMapper.INSTANCE.toDto(messageUpdated);
    }

    @Override
    public void delete(UUID id) {
        Message message = messageRepository
                .findById(id).orElseThrow(() -> new NoSuchElementException("Message not found"));

        message.getAttachments().forEach(attachment -> messageRepository.deleteById(attachment.getId()));
        messageRepository.deleteById(id);
    }

    private List<BinaryContentSave> getBinaryContents(List<MultipartFile> multipartFiles) {
        List<BinaryContentSave> binaryContents = new ArrayList<>();
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            for (MultipartFile multipartFile : multipartFiles) {
                try {
                    BinaryContent binaryContent = new BinaryContent(
                            multipartFile.getOriginalFilename(),
                            multipartFile.getSize(),
                            multipartFile.getContentType()
                    );
                    binaryContents.add(new BinaryContentSave(binaryContent, multipartFile.getBytes()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return binaryContents;
    }
}
