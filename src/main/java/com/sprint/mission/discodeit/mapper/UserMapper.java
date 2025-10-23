package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BinaryContentMapper.class})
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "online", expression = "java(userStatus != null && userStatus.isOnline())")
    // @Mapping(target = "profile", expression = "java(profile != null ? profile : null)")
    UserResponseDto toDto(User user, UserStatus userStatus, BinaryContentResponseDto profile);

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
