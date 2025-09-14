package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusRepository {
    ReadStatus save(ReadStatus binaryContent);
    Optional<ReadStatus> find(UUID id);
    Optional<List<ReadStatus>> findByChannelId(UUID ChannelId);
    Optional<List<ReadStatus>> findByUserId(UUID userId);
    Map<UUID, ReadStatus> findAll();
    void deleteByChannelId(UUID ChannelId);
    void delete(UUID id);
}
