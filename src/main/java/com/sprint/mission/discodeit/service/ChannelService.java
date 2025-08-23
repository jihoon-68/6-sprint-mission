package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {
    // Create
    Channel create(String title, String description, Channel.ChannelType type);

    // Read (single)
    Optional<Channel> findById(UUID id);
    Optional<Channel> findByTitle(String title);

    // Read (multiple)
    List<Channel> findAll();

    // Update
    Optional<Channel> update(UUID id, String title, String description);

    // Delete
    void delete(UUID id);
}
