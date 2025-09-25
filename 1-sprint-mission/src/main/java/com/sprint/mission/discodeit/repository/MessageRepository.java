package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.time.Instant;
import java.util.*;

public interface MessageRepository {
//    Message save(Message message);
//    Optional<Message> findById(UUID id);
//    List<Message> findAll();
//    boolean existsById(UUID id);
//    void deleteById(UUID id);
//    Map<UUID, Optional<Instant>> findLatestTimestampByChannelIds(Set<UUID> channelIds);

    Message save(Message message);
    Optional<Message> findById(UUID id);
    List<Message> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
    void deleteByChannelId(UUID channelId);
}
