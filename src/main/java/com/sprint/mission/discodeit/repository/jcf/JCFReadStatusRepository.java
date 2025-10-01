package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFReadStatusRepository implements ReadStatusRepository {

    private final List<ReadStatus> data = new ArrayList<>();

    @Override
    public void save(ReadStatus readStatus) {
        data.removeIf(rs -> rs.getId().equals(readStatus.getId()));
        data.add(readStatus);
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return data.stream()
                .filter(readStatus -> readStatus.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return data.stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<ReadStatus> findAllByChannelId(UUID channelId) {
        return data.stream()
                .filter(readStatus -> readStatus.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        ReadStatus readStatus = findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 ReadStatus입니다."));
        data.remove(readStatus);
    }

    @Override
    public void clear() {
        data.clear();
    }
}
