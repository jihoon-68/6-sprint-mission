package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@ConditionalOnProperty(
        prefix = "discodeit.repository",
        name = "type",
        havingValue = "jcf"
)
public class JCFReadStatusRepository implements ReadStatusRepository {
    private final Map<UUID, ReadStatus> map = new HashMap<>();

    @Override
    public ReadStatus save(ReadStatus binaryContent) {
        map.put(binaryContent.getId(), binaryContent);
        return binaryContent;
    }

    @Override
    public Optional<ReadStatus> find(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<List<ReadStatus>> findByChannelId(UUID channelId) {
        return Optional.of(map.entrySet()
                .stream()
                .filter(x -> x.getValue().getChannelID().equals(channelId))
                .map(x -> x.getValue())
                .collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public Optional<List<ReadStatus>> findByUserId(UUID userId) {
        return Optional.of(map.entrySet()
                .stream()
                .filter(x -> x.getValue().getUserID().equals(userId))
                .map(x -> x.getValue())
                .collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public Map<UUID, ReadStatus> findAll() {
        return Map.of();
    }

    @Override
    public boolean deleteByChannelId(UUID ChannelId) {
        return map.entrySet().removeIf(x -> x.getValue().getChannelID().equals(ChannelId));
    }

    @Override
    public void delete(UUID id) {
        map.remove(id);
    }
}
