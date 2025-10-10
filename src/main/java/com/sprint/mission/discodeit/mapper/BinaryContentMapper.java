package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BinaryContentMapper {

    BinaryContentResponseDto toDto(BinaryContent binaryContent);

//    public static BinaryContentResponseDto toDto(BinaryContent binaryContent) {
//        return BinaryContentResponseDto.builder()
//                .id(binaryContent.getId())
//                .fileName(binaryContent.getFileName())
//                .extension(binaryContent.getExtension())
//                .size(binaryContent.getSize())
//                .type(binaryContent.getType())
//                .data(binaryContent.getData())
//                .build();
//    }
}
