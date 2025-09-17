package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.UUID;

public interface BinaryContentRepository {
    BinaryContent findByUserId(UUID userId);
    void save(BinaryContent binaryContent);
    List<BinaryContent> findByMessageId(UUID messageId);

    void deleteByUserId(UUID id);

    void deleteByMessageId(UUID id);
}
