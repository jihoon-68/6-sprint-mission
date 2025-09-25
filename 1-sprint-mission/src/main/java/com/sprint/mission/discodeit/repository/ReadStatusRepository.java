package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.*;

public interface ReadStatusRepository {
    ReadStatus save(ReadStatus status);
    Optional<ReadStatus> find(UUID id);
    Optional<List<ReadStatus>> findByChannelId(UUID ChannelId);
    Optional<List<ReadStatus>> findByUserId(UUID userId);
    Map<UUID, ReadStatus> findAll();
    boolean deleteByChannelId(UUID ChannelId);
    void delete(UUID id);
}
