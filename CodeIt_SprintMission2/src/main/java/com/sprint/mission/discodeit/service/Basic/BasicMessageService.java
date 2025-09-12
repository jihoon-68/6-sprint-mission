package com.sprint.mission.discodeit.service.Basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public BasicMessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Message createMessage(UUID senderId, UUID receiverId, String contents) {
        if (userRepository.getUserById(senderId) == null) {
            System.out.println("메시지 생성 실패: 존재하지 않는 발신자 ID");
            return null;
        }
        if (userRepository.getUserById(receiverId) == null) {
            System.out.println("메시지 생성 실패: 존재하지 않는 수신자 ID");
            return null;
        }
        return messageRepository.createMessage(senderId, receiverId, contents);
    }

    @Override
    public Message getMessageById(UUID id) {
        Message message = messageRepository.getMessageById(id);
        if (message == null) {
            System.out.println("해당 ID에 맞는 메시지가 없습니다.");
        }
        return message;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    @Override
    public Message updateMessage(UUID id, String newContents) {
        return messageRepository.updateMessage(id, newContents);
    }

    @Override
    public boolean deleteMessage(UUID id) {
        return messageRepository.deleteMessage(id);
    }

}
