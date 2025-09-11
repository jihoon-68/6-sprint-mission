package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.UUID;

public interface ReadStatusRepository {
     List<ReadStatus> findAllByUserId(UUID userId);

    void save(ReadStatus readStatus);

    void deleteByChannelId(UUID id);

    ReadStatus findById(UUID id);

    void deleteById(UUID id);
}
