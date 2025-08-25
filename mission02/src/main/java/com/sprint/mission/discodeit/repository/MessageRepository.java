package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    void save(Message message);

    void remove(Message message);

    List<Message> findAll();

    List<Message> findByReceiverId(UUID receiverId);

    Optional<Message> findById(UUID messageId) throws NotFoundException;

    boolean existsById(UUID messageId);
}
