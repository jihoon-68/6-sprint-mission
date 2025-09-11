package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.UUID;

public interface ReadStatusRepository {
    void save(ReadStatus readStatus);
    ReadStatus findById(UUID id);

    List<ReadStatus> findAllByUserId(UUID id);
    List<ReadStatus> findAllByChannelId(UUID id);
    void deleteById(UUID id);
    void clear();
}
