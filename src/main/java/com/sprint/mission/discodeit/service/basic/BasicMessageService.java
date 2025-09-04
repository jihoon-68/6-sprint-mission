package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.Message.CreateMessageDto;
import com.sprint.mission.discodeit.dto.Message.UpdateMessageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    //
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;


    @Override
    public Message create(CreateMessageDto createMessageDto) {
        if (!channelRepository.existsById(createMessageDto.channelId())) {
            throw new NoSuchElementException("Channel not found with id " + createMessageDto.channelId());
        }
        if (!userRepository.existsById(createMessageDto.authorId())) {
            throw new NoSuchElementException("Author not found with id " + createMessageDto.authorId());
        }

        Message message = new Message(createMessageDto.content(), createMessageDto.channelId(), createMessageDto.authorId());

        if(createMessageDto.attatchmentUrls() != null) {
            for (String url : createMessageDto.attatchmentUrls()) {
                BinaryContent binaryContent = new BinaryContent(null,message.getId(),url);
                binaryContentRepository.save(binaryContent);
            }
        }

        return messageRepository.save(message);
    }

    @Override
    public Message find(UUID messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
    }

    @Override
    public List<Message> findallByChannelId(UUID channelId) {
        List<Message> messages = messageRepository.findAll();
        if(messages == null || messages.isEmpty()) {
            throw new NoSuchElementException("Channel not found with id " + channelId);
        }

        return messages.stream()
                .filter(x -> x.getChannelId().equals(channelId))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Message update(UpdateMessageDto updateMessageDto) {
        Message message = messageRepository.findById(updateMessageDto.messageId())
                .orElseThrow(() -> new NoSuchElementException("Message with id " + updateMessageDto.messageId() + " not found"));
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
}
