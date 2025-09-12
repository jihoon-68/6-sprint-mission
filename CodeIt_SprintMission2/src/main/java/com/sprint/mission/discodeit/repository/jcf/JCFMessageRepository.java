package com.sprint.mission.discodeit.repository.jcf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
// import com.sprint.mission.discodeit.service.UserService;

public class JCFMessageRepository implements MessageRepository {

    private final Map<UUID, Message> data = new HashMap<>();
    // private final UserService userService;

    public JCFMessageRepository() {
    }

    @Override
    public Message createMessage(UUID senderId, UUID receiverId, String contents) {
        // 연관된 도메인(User) 존재 검증 로직 제거
        Message message = new Message(senderId, receiverId, contents);
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