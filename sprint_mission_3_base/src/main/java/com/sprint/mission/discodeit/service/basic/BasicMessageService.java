package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.Message.CreateMessageRequest;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;


    @Override
    public Message create(CreateMessageRequest request) {
        if (!channelRepository.existsById(request.channelId())) {
            throw new NoSuchElementException("Channel not found with id " + request.channelId());
        }
        if (!userRepository.existsById(request.authorId())) {
            throw new NoSuchElementException("Author not found with id " + request.authorId());
        }

        Message message = new Message(request.content(), request.channelId(), request.authorId());

        if(request.attachments() != null) {
            for (String attachment : request.attachments()) {
                BinaryContent binaryContent = new BinaryContent(null, message.getId(), attachment);
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
    public List<Message> findAllByChannelId(UUID channelId) {
        List<Message> messages = messageRepository.findAll();

        if(messages == null || messages.isEmpty()) {
            throw new NoSuchElementException("Messages not found with channel id " + channelId);
        }

        return messages.stream()
                .filter(x->x.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public Message update(UpdateMessageRequest request) {
        Message message = messageRepository.findById(request.messageId())
                .orElseThrow(() -> new NoSuchElementException("Message with id " + request.messageId() + " not found"));
        message.update(request.content());
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
