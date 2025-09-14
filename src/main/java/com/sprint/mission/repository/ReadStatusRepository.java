package com.sprint.mission.repository;

import com.sprint.mission.dto.readstatus.ReadStatusCreateDto;
import com.sprint.mission.entity.ReadStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusRepository {

    ReadStatus save(ReadStatusCreateDto readStatusCreateDto);
    Optional<ReadStatus> findById(UUID id);
    List<ReadStatus> findByUserId(UUID userId);
    List<ReadStatus> findByChannelId(UUID channelId);
    List<ReadStatus> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
    void deleteByUserId(UUID userId);
    void deleteByChannelId(UUID channelId);
}
