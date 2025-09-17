package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.repository.RepositoryMessageConstants.READ_STATUS_ALREADY_EXISTS;
import static com.sprint.mission.discodeit.repository.RepositoryMessageConstants.READ_STATUS_ID_ALREADY_EXISTS;

public abstract class AbstractReadStatusRepository implements ReadStatusRepository {

    protected AbstractReadStatusRepository() {
    }

    @Override
    public ReadStatus save(ReadStatus readStatus) {
        Validator<ReadStatus> validator = Validator.identity();
        if (readStatus.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    ReadStatus::getId,
                    () -> new IllegalStateException(READ_STATUS_ID_ALREADY_EXISTS.formatted(readStatus.getId()))
            ));
        }
        validator = validator.and(Validator.uniqueKeys(
                ReadStatus::getUserId,
                ReadStatus::getChannelId,
                () -> new IllegalStateException(READ_STATUS_ALREADY_EXISTS.formatted(
                        "%s:%s".formatted(readStatus.getUserId(), readStatus.getChannelId()))))
        );
        Map<UUID, ReadStatus> data = getData();
        ReadStatus validated = validator.validate(data, readStatus);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public Optional<ReadStatus> find(UUID id) {
        Map<UUID, ReadStatus> data = getData();
        ReadStatus readStatus = data.get(id);
        return Optional.ofNullable(readStatus);
    }

    @Override
    public Set<ReadStatus> findAll() {
        Map<UUID, ReadStatus> data = getData();
        return new HashSet<>(data.values());
    }

    @Override
    public Set<ReadStatus> findAll(UUID userId) {
        Map<UUID, ReadStatus> data = getData();
        Map<UUID, Set<ReadStatus>> userIdToReadStatuses = groupByUserId(data);
        return userIdToReadStatuses.get(userId);
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, ReadStatus> data = getData();
        data.remove(id);
        flush(data);
    }

    @Override
    public void delete(UUID userId, UUID channelId) {
        Map<UUID, ReadStatus> data = getData();
        data = data.values()
                .stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId))
                .filter(readStatus -> readStatus.getChannelId().equals(channelId))
                .collect(Collectors.toMap(ReadStatus::getId, Function.identity()));
        flush(data);
    }

    private Map<UUID, Set<ReadStatus>> groupByUserId(Map<UUID, ReadStatus> data) {
        return data.values()
                .stream()
                .collect(Collectors.groupingBy(ReadStatus::getUserId, Collectors.toSet()));
    }

    protected abstract Map<UUID, ReadStatus> getData();

    protected void flush(Map<?, ?> data) {
    }
}
