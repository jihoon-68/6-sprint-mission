package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.dto.Message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.Message.MessageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentStorage binaryContentStorage;

    @Override
    public MessageDto create(List<MultipartFile> multipartFiles, MessageCreateRequest messageCreateRequest) {

        User user = userRepository.findById(messageCreateRequest.authorId())
                .orElseThrow(()-> new IllegalArgumentException("User with id: " + messageCreateRequest.authorId() + " not found"));

        Channel channel = channelRepository.findById(messageCreateRequest.channelId())
                .orElseThrow(()-> new IllegalArgumentException("Channel with id: " + messageCreateRequest.channelId() + " not found"));

        if (!multipartFiles.isEmpty()) {
            List<BinaryContent> binaryContents = getBinaryContents(multipartFiles);
            binaryContentRepository.saveAll(binaryContents);
            Message message = new Message(user, channel, messageCreateRequest.content(), binaryContents);
            messageRepository.save(message);

            return  messageMapper.toDto(message);
        }

        Message message = messageRepository.save(new Message(user, channel, messageCreateRequest.content()));
        return messageMapper.toDto(message);
    }

    @Override
    public List<MessageDto> findAllByChannelId(UUID channelId) {
        List<Message> messages = messageRepository.findAll();

        return messages.stream()
                .filter(message -> message.getChannel().getId().equals(channelId))
                .map(messageMapper::toDto)
                .toList();
    }

    @Override
    public MessageDto update(UUID messageId, String newContent) {
        Message messageUpdated = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        messageUpdated.update(newContent);
        messageRepository.save(messageUpdated);

        return messageMapper.toDto(messageUpdated);
    }

    @Override
    public void delete(UUID id) {
        Message message = messageRepository
                .findById(id).orElseThrow(() -> new NoSuchElementException("Message not found"));

        message.getAttachmentIds().forEach(attachment -> messageRepository.deleteById(attachment.getId()));
        messageRepository.deleteById(id);
    }

    private List<BinaryContent> getBinaryContents(List<MultipartFile> multipartFiles) {
        List<BinaryContent> binaryContents = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                BinaryContent binaryContent = new BinaryContent(
                        multipartFile.getOriginalFilename(),
                        multipartFile.getSize(),
                        multipartFile.getContentType()
                );
                binaryContents.add(binaryContent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return binaryContents;
    }
}
