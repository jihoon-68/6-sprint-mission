package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageDto.CreateMessageDto;
import com.sprint.mission.discodeit.dto.MessageDto.UpdateMessageDto;
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
        return messageRepository.save(message);
    }

    @Override
    public Message find(UUID messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        return messageRepository.findAll().stream().filter(message -> message.getChannelId().equals(channelId)).toList();
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
        binaryContentRepository.deleteById(messageId); //delete id?
    }
}
