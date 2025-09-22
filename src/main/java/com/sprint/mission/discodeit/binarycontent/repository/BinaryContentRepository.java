package com.sprint.mission.discodeit.binarycontent.repository;

import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent.OwnerType;
import com.sprint.mission.discodeit.common.persistence.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BinaryContentRepository extends CrudRepository<BinaryContent, UUID> {

    Optional<BinaryContent> findByOwnerTypeAndOwnerId(OwnerType ownerType, UUID ownerId);

    Iterable<BinaryContent> findAllByOwnerTypeAndOwnerId(OwnerType ownerType, UUID ownerId);
}
