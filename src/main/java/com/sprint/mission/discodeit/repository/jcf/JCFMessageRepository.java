package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class JCFMessageRepository implements MessageRepository {

    private List<Message> data = new ArrayList<>();

    @Override
    public void save(Message message) {
        data.add(message);
    }

    // 단건 조회 (ID로 조회, 관리자용)
    @Override
    public Message findMessageById(String messageId) {
        for (Message message : data) {
            if (message.getId().equals(messageId)) {
                return message;
            }
        }
        System.out.println("메시지가 존재하지 않습니다.");
        return null;
    }

    // 채널별 메시지 조회
    @Override
    public List<Message> findMessagesByChannel(Channel channel) {
        List<Message> messages = new ArrayList<>();
        for (Message message : data) {
            if (message.getChannel().equals(channel)) {
                messages.add(message);
            }
        }
        if (messages.isEmpty()) System.out.println("메시지가 존재하지 않습니다.");
        return messages;
    }

    @Override
    public void delete(Message message) {
        data.remove(message);
    }
}
