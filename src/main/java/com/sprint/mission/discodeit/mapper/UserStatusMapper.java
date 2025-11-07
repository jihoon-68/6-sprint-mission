package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponseDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserStatusMapper {

    UserStatusMapper INSTANCE = Mappers.getMapper(UserStatusMapper.class);

    @Mapping(source = "user.id", target = "userId")
    UserStatusResponseDto toDto(UserStatus userStatus);

    @Mapping(source = "dto.userId", target = "user.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastActiveAt", ignore = true)
    UserStatus toEntity(UserStatusCreateRequestDto dto);

//    public static UserStatusResponseDto toDto(UserStatus userStatus) {
//        return UserStatusResponseDto.builder()
//                .id(userStatus.getId())
//                .userId(userStatus.getUser().getId())
//                .lastActiveAt(userStatus.getLastActiveAt())
//                .build();
//    }
}
