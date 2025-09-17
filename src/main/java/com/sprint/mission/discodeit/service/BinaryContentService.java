package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binaryContent.model.BinaryContentDto;
import com.sprint.mission.discodeit.dto.binaryContent.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.binaryContent.response.BinaryContentFindAllResponse;

import java.util.UUID;

public interface BinaryContentService {
    void createBinaryContent(BinaryContentCreateRequest request);
    BinaryContentFindAllResponse findAllByMessageId(UUID id);
}
