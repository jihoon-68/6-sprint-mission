package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;

import java.util.*;

public class JCFReadStatusRepository implements ReadStatusRepository {

    private final Map<UUID, ReadStatus> readStatusMap;

    public JCFReadStatusRepository() {
        this.readStatusMap = new HashMap<>();
    }

    @Override
    public ReadStatus save(ReadStatus readStatus) {
        this.readStatusMap.put(readStatus.getId(), readStatus);
        return readStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return Optional.ofNullable(this.readStatusMap.get(id));
    }

    @Override
    public List<ReadStatus> findAll() {
        return this.readStatusMap.values().stream().toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return this.readStatusMap.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.readStatusMap.remove(id);
    }
}
