package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface MessageService {
    // Create
    Message create(String content, User user, Channel channel, Message.MessageType type);

    // Read (single)
    Optional<Message> findById(UUID id);

    // Read (multiple)
    List<Message> findAll();

    // Update
    Optional<Message> update(UUID id, String content);

    // Delete
    void delete(UUID id);
}
