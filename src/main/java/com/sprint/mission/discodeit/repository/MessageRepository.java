package com.sprint.mission.discodeit.repository;


import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    void save(Message message);

    void deleteById(UUID id);

    Message findById(UUID id);

    List<Message> findByAuthorIdAndChannelId(UUID authorId, UUID channelId);

    List<Message> findAll();

    List<Message> findByChannelId(UUID id);

    boolean existsById(UUID id);

    void deleteByChannelId(UUID id);
}
