package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.BinaryContent.OwnerType;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BinaryContentRepository extends CrudRepository<BinaryContent, UUID> {

    Optional<BinaryContent> findUserProfile(UUID userId);

    Set<BinaryContent> findAll(OwnerType ownerType);

    Set<BinaryContent> findAllMessageAttachments(UUID messageId);
}
