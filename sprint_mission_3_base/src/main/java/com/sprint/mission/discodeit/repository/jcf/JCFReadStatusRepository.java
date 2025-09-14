package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@ConditionalOnProperty(name = "repository.type", havingValue = "jcf")
public class JCFReadStatusRepository implements ReadStatusRepository {
    private final Map<UUID, ReadStatus> data;

    public JCFReadStatusRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public ReadStatus save(ReadStatus binaryContent) {
        data.put(binaryContent.getId(), binaryContent);
        return binaryContent;
    }

    @Override
    public Optional<ReadStatus> find(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<List<ReadStatus>> findByChannelId(UUID ChannelId) {
        return Optional.of(
                data.entrySet()
                .stream()
                .filter(x -> x.getValue().getChannelId().equals(ChannelId))
                .map(x -> x.getValue())
                .toList()
        );
    }

    @Override
    public Map<UUID, ReadStatus> findAll() {
        return this.data;
    }

    @Override
    public Optional<List<ReadStatus>> findByUserId(UUID userId) {
        return Optional.of(
                data.entrySet()
                .stream()
                .filter(x -> x.getValue().getUserId().equals(userId))
                .map(x -> x.getValue())
                .toList()
        );
    }

    @Override
    public void deleteByChannelId(UUID ChannelId) {
        data.values().removeIf(content -> content.getChannelId().equals(ChannelId));
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
