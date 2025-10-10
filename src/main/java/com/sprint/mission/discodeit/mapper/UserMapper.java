package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BinaryContentMapper.class})
public interface UserMapper {

    @Mapping(target = "online", ignore = true)
    UserResponseDto toDto(User user);

//    public static UserResponseDto toDto(User user, Boolean online) {
//        return UserResponseDto.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
//                .online(online)
//                .build();
//    }
}
