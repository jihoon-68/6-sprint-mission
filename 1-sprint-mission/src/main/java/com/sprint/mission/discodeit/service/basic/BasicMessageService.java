package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTOs.Message.MessageCreateRequest;
import com.sprint.mission.discodeit.DTOs.Message.UpdateMessageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.exception.FileStorageException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class BasicMessageService implements MessageService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    //
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    public BasicMessageService(MessageRepository messageRepository,
                               ChannelRepository channelRepository,
                               UserRepository userRepository,
                               BinaryContentRepository binaryContentRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.binaryContentRepository = binaryContentRepository;
    }

    @Override
    public Message create(MessageCreateRequest messageCreateRequest) {
        if (!channelRepository.existsById(messageCreateRequest.channelId())) {
            throw new NoSuchElementException("Channel not found with id " + messageCreateRequest.channelId());
        }
        if (!userRepository.existsById(messageCreateRequest.authorId())) {
            throw new NoSuchElementException("Author not found with id " + messageCreateRequest.authorId());
        }

        Message message = new Message(messageCreateRequest.content(), messageCreateRequest.channelId(), messageCreateRequest.authorId());

        if(messageCreateRequest.attachments() != null) {
            for (String url : messageCreateRequest.attachments()) {
                BinaryContent binaryContent = new BinaryContent(messageCreateRequest.authorId(),message.getId(),url);
                binaryContentRepository.save(binaryContent);
            }
        }

        return messageRepository.save(message);
    }

    @Override
    public Message find(UUID messageId) {
        return getMessageById(messageId);
    }

    @Override
    public List<Message> findallByChannelId(UUID channelId) {
        return messageRepository.findAll().stream()
                        .filter(m -> m.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public Message update(UpdateMessageDto updateMessageDto) {
        Message message = getMessageById(updateMessageDto.messageId());
        message.update(updateMessageDto.newContent());
        return messageRepository.save(message);
    }

    @Override
    public void delete(UUID messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new NoSuchElementException("Message with id " + messageId + " not found");
        }

        messageRepository.deleteById(messageId);

        binaryContentRepository.deleteByMessageId(messageId);
    }

    private Message getMessageById(UUID messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
    }
}
