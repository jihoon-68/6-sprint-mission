package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BinaryContentMapper {

    default BinaryContent ofUserProfile(UUID userId, byte[] profileImage) {
        return BinaryContent.ofUserProfile(userId, profileImage);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BinaryContent fromUserProfile(BinaryContentDto.Request request);

    default BinaryContent ofMessageAttachment(UUID messageId, byte[] binary) {
        return BinaryContent.ofMessageAttachment(messageId, binary);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BinaryContent fromMessageAttachment(BinaryContentDto.Request request);

    BinaryContentDto.Response toResponse(BinaryContent binaryContent);
}
