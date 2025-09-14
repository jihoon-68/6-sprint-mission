package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.MessageResponse;
import com.sprint.mission.discodeit.dto.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public MessageResponse create(CreateMessageRequest request) {
        if (!channelRepository.existsById(request.channelId())) {
            throw new NoSuchElementException("Channel not found with id " + request.channelId());
        }
        if (!userRepository.existsById(request.authorId())) {
            throw new NoSuchElementException("Author not found with id " + request.authorId());
        }

        Message message = new Message(request.content(), request.channelId(), request.authorId());

        IntStream.range(0, request.attachmentCount()).forEach(i -> {
            BinaryContent attachment = new BinaryContent();
            binaryContentRepository.save(attachment);
            message.addAttachment(attachment.getId());
        });

        messageRepository.save(message);
        return MessageResponse.of(message);
    }

    @Override
    public MessageResponse find(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
        return MessageResponse.of(message);
    }

    @Override
    public List<MessageResponse> findAllByChannelId(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel not found with id " + channelId);
        }
        return messageRepository.findByChannelId(channelId).stream()
                .map(MessageResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse update(UUID messageId, UpdateMessageRequest request) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
        message.update(request.content());
        messageRepository.save(message);
        return MessageResponse.of(message);
    }

    @Override
    public void delete(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));

        message.getAttachmentIds().forEach(binaryContentRepository::deleteById);

        messageRepository.deleteById(messageId);
    }
}
