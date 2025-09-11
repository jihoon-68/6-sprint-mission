package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.AuthDto;
import com.sprint.mission.discodeit.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    AuthDto.Response toResponse(User user);
}
