package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.reqeust.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void createMessage(UUID authorId, UUID channelId, UUID receiverId, String content, List<String> filePath) {
        if (content.trim().isEmpty()) {
            log.info("입력되지 않은 content - authorId: {}, channelId: {}, receiverId: {}", authorId, channelId, receiverId);
            throw new IllegalArgumentException("content는 한 글자 이상 입력해야 합니다.");
        }

        Message message = new Message(authorId, channelId, receiverId, content, userRepository.findById(receiverId) == null);

        if (filePath != null) {
            for (String path : filePath) {
                BinaryContent binaryContent = new BinaryContent(null, message.getId(), path);
                binaryContentRepository.save(binaryContent);
            }
        }
        messageRepository.save(message);
    }

    @Override
    public void updateContent(MessageUpdateRequest request) {
        Message message = messageRepository.findById(request.getId());

        if (message == null) {
            log.warn("Message를 찾을 수 없습니다. - messageId: {}", request.getId());
            throw new IllegalArgumentException("message를 찾을 수 없습니다. messageId: " + request.getId());
        }
        if (request.getContent().trim().isEmpty()) {
            log.info("입력되지 않은 content - messageId: {}", request.getId());
            throw new IllegalArgumentException("message는 한 글자 이상 입력해야 합니다.");
        }
        message.updateContent(request.getContent());
        messageRepository.save(message);
    }

    @Override
    public void deleteMessageById(UUID id) {
        Message message = messageRepository.findById(id);
        if (message == null) {
            log.warn("message를 찾을 수 없습니다. - messageId: {}", id);
            throw new IllegalArgumentException("message를 찾을 수 없습니다.");
        }
        List<BinaryContent> binaryContents = binaryContentRepository.findByMessageId(id);
        if (binaryContents != null &&  !binaryContents.isEmpty()) {
            binaryContentRepository.deleteByMessageId(id);
        }
        messageRepository.deleteById(id);
    }

    @Override
    public void deleteMessageByAuthorId(UUID authorId) {
        List<UUID> ids = messageRepository.findAll()
                .stream().filter(message -> message.getAuthorId().equals(authorId))
                .map(Message::getId).toList();

        for (UUID id : ids) {
            binaryContentRepository.deleteByMessageId(id);
            messageRepository.deleteById(id);
        }
    }

    @Override
    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message findMessageById(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findAllByAuthorIdAndChannelId(UUID authorId, UUID channelId) {
        return messageRepository.findByAuthorIdAndChannelId(authorId, channelId);
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        return messageRepository.findByChannelId(channelId);
    }

    @Override
    public List<Message> findFriendConversation(UUID userId, UUID friendId) {
        List<Message> messages = messageRepository.findAll();

        return messages.stream().filter(message -> (Objects.equals(message.getAuthorId(), userId) && Objects.equals(message.getReceiverId(), friendId))
                || (Objects.equals(message.getAuthorId(), friendId) && Objects.equals(message.getReceiverId(), userId))).toList();
    }

    @Override
    public List<Message> findMyMessagesToFriend(UUID userId, UUID friendId) {
        List<Message> messages = messageRepository.findAll();

        return messages.stream().filter(message -> Objects.equals(message.getAuthorId(), userId) && Objects.equals(message.getReceiverId(), friendId)).toList();
    }

    @Override
    public void deleteAllByChannelIds(List<UUID> channelIds) {
        for (UUID id : channelIds) {
            List<Message> messages = messageRepository.findByChannelId(id);
            for (Message message : messages) {
                binaryContentRepository.deleteByMessageId(message.getId());
                messageRepository.deleteById(message.getId());
            }
        }
    }

    @Override
    public void anonymizeUserMessage(UUID userId) {
        List<Message> messages = messageRepository.findAll().stream().filter(message -> Objects.equals(message.getAuthorId(), userId) ||
                Objects.equals(message.getReceiverId(), userId)).toList();

        for (Message message : messages) {
            if (Objects.equals(message.getAuthorId(), userId)) {
                message.updateIsDrawnAuthor(true);
            } else {
                message.updateIsDrawnReceiver(true);
            }
            messageRepository.save(message);
        }
    }

    @Override
    public void cleanupDM() {
        List<Message> messages = messageRepository.findAll().stream().filter(message -> message.isDrawnAuthor() && message.isDrawnReceiver()).toList();
        for (Message message : messages) {
            binaryContentRepository.deleteByMessageId(message.getId());
            messageRepository.deleteById(message.getId());
        }
    }
}