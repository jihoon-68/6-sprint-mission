package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@ConditionalOnProperty(
        prefix = "discodeit.repository",
        name = "type",
        havingValue = "jcf",
        matchIfMissing = true
)
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
