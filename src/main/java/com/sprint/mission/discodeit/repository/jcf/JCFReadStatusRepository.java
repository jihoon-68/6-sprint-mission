package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
@Repository
public class JCFReadStatusRepository implements ReadStatusRepository {
    private final List<ReadStatus> readStatusesDate;

    public JCFReadStatusRepository(List<ReadStatus> readStatuses) {
        this.readStatusesDate = readStatuses;
    }

    @Override
    public ReadStatus save(ReadStatus ReadStatus) {
        int idx = readStatusesDate.indexOf(ReadStatus);
        if (idx >=0) {
            readStatusesDate.set(idx, ReadStatus);
        }else {
            readStatusesDate.add(ReadStatus);
        }
        return ReadStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return readStatusesDate.stream()
                .filter(rs -> rs.getId().equals(id))
                .findAny();
    }

    @Override
    public List<ReadStatus> findAll() {
        return List.copyOf(readStatusesDate);
    }

    @Override
    public boolean existsById(UUID id) {
        return readStatusesDate.stream()
        .anyMatch(rs -> rs.getId().equals(id));
    }

    @Override
    public void deleteById(UUID id) {
        readStatusesDate.removeIf(rs -> rs.getId().equals(id));
    }
}
