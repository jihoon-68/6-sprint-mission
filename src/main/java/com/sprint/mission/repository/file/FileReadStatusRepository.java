package com.sprint.mission.repository.file;

import com.sprint.mission.dto.readstatus.ReadStatusCreateDto;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.ReadStatus;
import com.sprint.mission.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileReadStatusRepository extends SaveAndLoadCommon<ReadStatus> implements ReadStatusRepository {

    public FileReadStatusRepository() {
        super(ReadStatus.class);
    }
    @Override
    public ReadStatus save(ReadStatusCreateDto readStatusCreateDto) {
        ReadStatus readStatus = new ReadStatus(readStatusCreateDto.getUserId(), readStatusCreateDto.getChannelId());
        save(readStatus);
        return readStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        if(load(id).isEmpty()) return Optional.empty();
        return load(id);
    }

    @Override
    public List<ReadStatus> findByUserId(UUID userId) {
        List<ReadStatus> readStatusList = loadAll();
        return readStatusList.stream().filter(readStatus -> readStatus.getUserId().equals(userId)).toList();
    }

    @Override
    public List<ReadStatus> findByChannelId(UUID channelId) {
        List<ReadStatus> readStatusList = loadAll();
        return readStatusList.stream().filter(readStatus -> readStatus.getChannelId().equals(channelId)).toList();
    }

    @Override
    public List<ReadStatus> findAll() {
        return loadAll();
    }

    @Override
    public boolean existsById(UUID id) {
        List<ReadStatus> readStatuses = loadAll();
        return readStatuses.stream().anyMatch(readStatus -> readStatus.getId().equals(id));
    }

    @Override
    public void deleteById(UUID id) {
        delete(id);
    }

    @Override
    public void deleteByUserId(UUID userId) {

    }

    @Override
    public void deleteByChannelId(UUID channelId) {
        List<ReadStatus> readStatusList = findByChannelId(channelId);
        readStatusList.forEach(readStatus -> delete(readStatus.getId()));
    }
}
