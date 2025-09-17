package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.BinaryContent.OwnerType;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.repository.RepositoryMessageConstants.BINARY_CONTENT_ID_ALREADY_EXISTS;

public abstract class AbstractBinaryContentRepository implements BinaryContentRepository {

    protected AbstractBinaryContentRepository() {
    }

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        Validator<BinaryContent> validator = Validator.identity();
        if (binaryContent.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    BinaryContent::getId,
                    () -> new IllegalStateException(BINARY_CONTENT_ID_ALREADY_EXISTS.formatted(binaryContent.getId()))
            ));
        }
        Map<UUID, BinaryContent> data = getData();
        BinaryContent validated = validator.validate(data, binaryContent);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public Optional<BinaryContent> find(UUID id) {
        Map<UUID, BinaryContent> data = getData();
        BinaryContent binaryContent = data.get(id);
        return Optional.ofNullable(binaryContent);
    }

    @Override
    public Optional<BinaryContent> findUserProfile(UUID userId) {
        Map<UUID, BinaryContent> data = getData();
        Map<UUID, BinaryContent> userIdToUserProfile = groupByUserId(data);
        BinaryContent binaryContent = userIdToUserProfile.get(userId);
        return Optional.ofNullable(binaryContent);
    }

    @Override
    public Set<BinaryContent> findAll() {
        Map<UUID, BinaryContent> data = getData();
        return new HashSet<>(data.values());
    }

    @Override
    public Set<BinaryContent> findAll(OwnerType ownerType) {
        Map<UUID, BinaryContent> data = getData();
        return data.values()
                .stream()
                .filter(binaryContent -> binaryContent.getOwnerType() == ownerType)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<BinaryContent> findAllMessageAttachments(UUID messageId) {
        Map<UUID, BinaryContent> data = getData();
        Map<UUID, Set<BinaryContent>> messageIdToMessageAttachments = groupByMessageId(data);
        return messageIdToMessageAttachments.getOrDefault(messageId, Collections.emptySet());
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, BinaryContent> data = getData();
        data.remove(id);
        flush(data);
    }

    private Map<UUID, BinaryContent> groupByUserId(Map<UUID, BinaryContent> data) {
        return data.values()
                .stream()
                .filter(binaryContent -> binaryContent.getOwnerType() == OwnerType.USER_PROFILE)
                .collect(Collectors.toMap(BinaryContent::getOwnerId, Function.identity()));
    }

    private Map<UUID, Set<BinaryContent>> groupByMessageId(Map<UUID, BinaryContent> data) {
        return data.values()
                .stream()
                .filter(binaryContent -> binaryContent.getOwnerType() == OwnerType.MESSAGE_ATTACHMENT)
                .collect(Collectors.groupingBy(BinaryContent::getOwnerId, Collectors.toSet()));
    }

    protected abstract Map<UUID, BinaryContent> getData();

    protected void flush(Map<?, ?> data) {
    }
}
