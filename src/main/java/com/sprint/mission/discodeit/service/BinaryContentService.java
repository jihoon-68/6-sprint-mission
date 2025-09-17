package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent.OwnerType;

import java.util.Set;
import java.util.UUID;

public interface BinaryContentService {

    BinaryContentDto.Response create(BinaryContentDto.Request request);

    BinaryContentDto.Response read(UUID id);

    Set<BinaryContentDto.Response> readAll(OwnerType ownerType);

    void delete(UUID id);
}
