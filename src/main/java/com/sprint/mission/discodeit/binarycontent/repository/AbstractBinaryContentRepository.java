package com.sprint.mission.discodeit.binarycontent.repository;

import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent.OwnerType;
import com.sprint.mission.discodeit.common.exception.DiscodeitException.DiscodeitPersistenceException;
import com.sprint.mission.discodeit.common.persistence.Validator;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractBinaryContentRepository implements BinaryContentRepository {

    protected AbstractBinaryContentRepository() {
    }

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        Validator<BinaryContent> validator = Validator.identity();
        if (binaryContent.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    BinaryContent::getId,
                    binaryContentKey -> new DiscodeitPersistenceException(
                            "BinaryContent ID already exists: '%s'".formatted(binaryContentKey.getId()))
            ));
        }
        Map<UUID, BinaryContent> data = getData();
        BinaryContent validated = validator.validate(data, binaryContent);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public BinaryContent findById(UUID id) {
        Map<UUID, BinaryContent> data = getData();
        BinaryContent binaryContent = data.get(id);
        if (binaryContent == null) {
            throw new DiscodeitPersistenceException("BinaryContent not found for ID: '%s'".formatted(id));
        }
        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> findByOwnerTypeAndOwnerId(OwnerType ownerType, UUID ownerId) {
        Map<UUID, BinaryContent> data = getData();
        return data.values().stream()
                .filter(binaryContent -> binaryContent.getOwnerType() == ownerType)
                .filter(binaryContent -> binaryContent.getOwnerId().equals(ownerId))
                .findAny();
    }

    @Override
    public Iterable<BinaryContent> findAll() {
        Map<UUID, BinaryContent> data = getData();
        return new HashSet<>(data.values());
    }

    @Override
    public Iterable<BinaryContent> findAllByOwnerTypeAndOwnerId(OwnerType ownerType, UUID ownerId) {
        Map<UUID, BinaryContent> data = getData();
        Map<UUID, Set<BinaryContent>> messageIdToMessageAttachments = groupByMessageId(data);
        Set<BinaryContent> binaryContents = messageIdToMessageAttachments.get(ownerId);
        if (binaryContents == null) {
            return Collections.emptySet();
        }
        return binaryContents.stream()
                .filter(binaryContent -> binaryContent.getOwnerType() == ownerType)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void deleteById(UUID id) {
        Map<UUID, BinaryContent> data = getData();
        data.remove(id);
        flush(data);
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
