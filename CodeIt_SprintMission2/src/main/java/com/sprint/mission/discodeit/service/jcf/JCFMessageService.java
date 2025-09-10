package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFMessageService implements MessageService {

    private final Map<UUID, Message> data = new HashMap<>();
    private final UserService userService;

    public JCFMessageService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Message createMessage(UUID senderId, UUID receiverId, String contents) {
        if (userService.getUserById(senderId) == null) {
            System.out.println("메시지 생성 실패: 존재하지 않는 발신자 ID");
            return null;
        }
        if (userService.getUserById(receiverId) == null) {
            System.out.println("메시지 생성 실패: 존재하지 않는 수신자 ID");
            return null;
        }

        Message message = new Message(senderId, receiverId,contents);
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Message getMessageById(UUID id) {
        if (data.containsKey(id)) {
            return data.get(id);
        } else {
            System.out.println("해당 ID에 맞는 메시지가 없습니다.");
            return null;
        }
    }

    @Override
    public List<Message> getAllMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Message updateMessage(UUID id, String newContents) {
        Message message = data.get(id);
        message.updateMessage(newContents);
        return message;
    }

    @Override
    public boolean deleteMessage(UUID id) {
        return data.remove(id) != null;
    }
}