package com.sprint.mission.discodeit.dto.BinaryContent;

import java.util.UUID;

public record BinaryContentDto (
        UUID id,
        String fileName,
        Long size,
        String contentType
){
    public BinaryContentDto(UUID id, String fileName, Long size, String contentType){
        this(
                id,
                fileName,
                size,
                contentType,
                new byte[]{}
        );
    }
    public BinaryContentDto(BinaryContentDto binaryContentDto,byte[] bytes){
        this(
                binaryContentDto.id(),
                binaryContentDto.fileName(),
                binaryContentDto.size(),
                binaryContentDto.contentType(),
                bytes
        );
    }
}
