package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    public static List<Message> MessageDate;

    public JCFMessageRepository() {
        MessageDate = new ArrayList<Message>();
    }

    public Message createMessage(User sender, String message) {
        Message newMessage = new Message(sender, message);
        MessageDate.add(newMessage);
        return newMessage;
    }


    public Message findMessageById(UUID id) {
        return MessageDate.stream()
                .filter(message -> message.getMessageId().equals(id))
                .toList()
                .get(0);
    }

    public List<Message> findAllMessages() {
        return MessageDate;
    }


    public void updateMessage(Message message) {
        int idx = MessageDate.indexOf(message);
        if (idx == -1) {
            System.out.println("해당 메시지 없음");
            return;
        }
        MessageDate.set(idx, message);
    }

    public void deleteMessage(UUID id) {
        MessageDate.remove(findMessageById(id));
    }
}
