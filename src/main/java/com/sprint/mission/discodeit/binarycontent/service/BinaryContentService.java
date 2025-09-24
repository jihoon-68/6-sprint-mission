package com.sprint.mission.discodeit.binarycontent.service;

import com.sprint.mission.discodeit.binarycontent.BinaryContentDto.Request;
import com.sprint.mission.discodeit.binarycontent.BinaryContentDto.Response;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BinaryContentService {

    Response createBinaryContent(Request request);

    Response getBinaryContentById(UUID id);

    Optional<Response> getUserProfileByUserId(UUID userId);

    Set<Response> getMessageAttachmentsByMessageId(UUID messageId);

    void deleteBinaryContentById(UUID id);
}
