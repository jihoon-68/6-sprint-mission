package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BinaryContentMapper {

    BinaryContentMapper INSTANCE = Mappers.getMapper(BinaryContentMapper.class);

    BinaryContentDto toDto(BinaryContent binaryContent);
}
