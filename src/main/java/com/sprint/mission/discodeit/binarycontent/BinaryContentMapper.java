package com.sprint.mission.discodeit.binarycontent;

import com.sprint.mission.discodeit.binarycontent.BinaryContentDto.Request;
import com.sprint.mission.discodeit.binarycontent.BinaryContentDto.Response;
import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent;

import java.util.Base64;

public final class BinaryContentMapper {

    private BinaryContentMapper() {
    }

    public static BinaryContent from(Request request) {
        return BinaryContent.of(
                request.ownerType(),
                request.ownerId(),
                Base64.getDecoder().decode(request.bytesBase64())
        );
    }

    public static Response toResponse(BinaryContent binaryContent) {
        return new Response(
                binaryContent.getId(),
                binaryContent.getCreatedAt(),
                binaryContent.getOwnerType(),
                binaryContent.getOwnerId(),
                Base64.getEncoder().encodeToString(binaryContent.getBytes())
        );
    }
}
