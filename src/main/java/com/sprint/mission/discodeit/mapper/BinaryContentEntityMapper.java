package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.entity.BinaryContentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BinaryContentEntityMapper {

  BinaryContentDTO.BinaryContent toBinaryContent(BinaryContentEntity binaryContentEntity);

  BinaryContentEntity toEntity(BinaryContentDTO.BinaryContent binaryContentDTO);

}
