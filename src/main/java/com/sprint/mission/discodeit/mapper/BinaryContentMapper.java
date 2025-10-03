package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;

public class BinaryContentMapper {
    public static BinaryContentResponseDto toDto(BinaryContent binaryContent) {
        return BinaryContentResponseDto.builder()
                .id(binaryContent.getId())
                .fileName(binaryContent.getFileName())
                .extension(binaryContent.getExtension())
                .size(binaryContent.getSize())
                .type(binaryContent.getType())
                .data(binaryContent.getData())
                .build();
    }
}
