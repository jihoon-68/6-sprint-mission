package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface BinaryContentRepository {
    BinaryContent save(BinaryContent binaryContent);
    Optional<BinaryContent> find(UUID id);
    Optional<BinaryContent> findByUserId(UUID userId);
    Map<UUID, BinaryContent> findAll();
    void deleteByUserId(UUID userId);
    void deleteByMessageId(UUID messageId);
    void delete(UUID id);
}
