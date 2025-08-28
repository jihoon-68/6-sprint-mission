package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;

public interface MessageRepository {
    void save(Message message);
    Message findMessageById(String messageId);
    List<Message> findMessagesByChannel(Channel Channel);
    void delete(Message message);
}
