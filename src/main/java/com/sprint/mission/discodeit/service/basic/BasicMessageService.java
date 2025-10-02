package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public Message create(List<MultipartFile> multipartFiles, CreateMessageDTO createMessageDTO) {
        if (!userRepository.existsById(createMessageDTO.authorId())
                || !channelRepository.existsById(createMessageDTO.channelId())) {

            throw new NoSuchElementException("Sender Or Receiver channels are mandatory");
        }


        if (!multipartFiles.isEmpty()) {
            List<BinaryContent> binaryContents = getBinaryContents(multipartFiles);

            return messageRepository.save(new Message(
                    createMessageDTO.authorId(),
                    createMessageDTO.channelId(),
                    createMessageDTO.content(),
                    binaryContents
            ));
        }

        return messageRepository.save(new Message(
                createMessageDTO.authorId(),
                createMessageDTO.channelId(),
                createMessageDTO.content()
        ));
    }

    @Override
    public Message find(UUID id) {
        return messageRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Message not found"));
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        List<Message> messages = messageRepository.findAll();

        return messages.stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public Message update(UUID messageId, String newContent) {
        Message messageUpdated = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        messageUpdated.update(newContent);
        return messageRepository.save(messageUpdated);
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
                        multipartFile.getContentType(),
                        multipartFile.getBytes()
                );
                binaryContents.add(binaryContent);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return binaryContents;
    }
}
