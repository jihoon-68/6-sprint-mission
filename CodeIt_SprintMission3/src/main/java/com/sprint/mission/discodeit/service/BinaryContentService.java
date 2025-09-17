package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.CreateBinaryContentRequest;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContentResponse create(CreateBinaryContentRequest request);

    BinaryContentResponse find(UUID binaryContentId);

    List<BinaryContentResponse> findAllByIdIn(List<UUID> binaryContentIds);

    void delete(UUID binaryContentId);
}
