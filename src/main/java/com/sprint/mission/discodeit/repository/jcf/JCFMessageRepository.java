package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    public final List<Message> MessageDate;

    public JCFMessageRepository() {
        MessageDate = new ArrayList<Message>();
    }

    public void createMessage(Message message) {
        MessageDate.add(message);
    }


    public Message findMessageById(UUID id) {
        return MessageDate.stream()
                .filter(message -> message.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public List<Message> findAllMessages() {
        return MessageDate;
    }


    public void updateMessage(Message message) {

        int idx = MessageDate.indexOf(message);
        if (idx == -1) {
            throw new NullPointerException("해당 메시지 없습니다.");
        }
        MessageDate.set(idx, message);

    }

    public void deleteMessage(UUID id) {
        MessageDate.remove(findMessageById(id));
    }
}
