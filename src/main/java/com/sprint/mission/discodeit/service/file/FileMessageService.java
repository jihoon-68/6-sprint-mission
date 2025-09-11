package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.message.reqeust.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void createMessage(UUID authorId, UUID channelId, UUID receiverId, String content, List<String> filePath) {
        if (content.trim().isEmpty()) {
            System.out.println("[Error] 메시지는 1글자 이상 입력해주세요.");
            return;
        }

        Message message = new Message(authorId, channelId, receiverId, content, userRepository.findById(receiverId) == null);

        if (filePath != null) {
            for (String path : filePath) {
                BinaryContent binaryContent = new BinaryContent(null, message.getId(), path);
                binaryContentRepository.save(binaryContent);
            }
        }
        messageRepository.save(message);

        System.out.println("[Info] 메시지가 생성되었습니다.");
    }

    @Override
    public void updateContent(MessageUpdateRequest request) {
        Message message = messageRepository.findById(request.getId());

        if (message == null) {
            System.out.println("[Error] 메시지를 찾을 수 없습니다.");
            return;
        }
        if (request.getContent().trim().isEmpty()) {
            System.out.println("[Error] 메시지는 1글자 이상 입력해주세요.");
            return;
        }
        message.updateContent(request.getContent());
        messageRepository.save(message);

        System.out.println("[Info] 메시지 업데이트가 완료되었습니다.");
    }

    @Override
    public void deleteMessageById(UUID id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            System.out.println("[Info] 메시지가 삭제되었습니다.");
        } else {
            System.out.println("[Error] 메시지 삭제에 실패했습니다.");
        }
    }

    @Override
    public void deleteMessageByAuthorId(UUID authorId) {
        List<UUID> ids = messageRepository.findAll()
                .stream().filter(m -> m.getAuthorId().equals(authorId))
                .map(m -> m.getId()).toList();

        for (UUID id : ids) {
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

        return messages.stream().filter(m -> (Objects.equals(m.getAuthorId(), userId) && Objects.equals(m.getReceiverId(), friendId))
                || (Objects.equals(m.getAuthorId(), friendId) && Objects.equals(m.getReceiverId(), userId))).toList();
    }

    @Override
    public List<Message> findMyMessagesToFriend(UUID userId, UUID friendId) {
        List<Message> messages = messageRepository.findAll();

        return messages.stream().filter(m -> Objects.equals(m.getAuthorId(), userId) && Objects.equals(m.getReceiverId(), friendId)).toList();
    }

    @Override
    public void deleteAllByChannelIds(List<UUID> channelIds) {
        for (UUID id : channelIds) {
            List<Message> messages = messageRepository.findByChannelId(id);
            for (Message message : messages) {
                messageRepository.deleteById(message.getId());
            }
        }
    }

    @Override
    public void anonymizeUserMessage(UUID userId) {
        List<Message> messages = messageRepository.findAll().stream().filter(m -> Objects.equals(m.getAuthorId(), userId) ||
                Objects.equals(m.getReceiverId(), userId)).toList();

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
        List<Message> messages = messageRepository.findAll().stream().filter(m -> m.isDrawnAuthor() && m.isDrawnReceiver()).toList();
        for (Message message : messages) {
            messageRepository.deleteById(message.getId());
        }
    }
}