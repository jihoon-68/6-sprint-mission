package com.sprint.mission.discodeit.user;

import com.sprint.mission.discodeit.user.UserDto.Request;
import com.sprint.mission.discodeit.user.UserDto.Response;
import com.sprint.mission.discodeit.user.UserDto.ResponseWithLastActivatedAt;
import com.sprint.mission.discodeit.user.UserDto.ResponseWithOnline;
import com.sprint.mission.discodeit.user.domain.User;
import com.sprint.mission.discodeit.user.domain.UserCredentials;
import com.sprint.mission.discodeit.user.domain.UserStatus;
import org.springframework.lang.Nullable;

import java.util.UUID;

public final class UserMapper {

    private UserMapper() {
    }

    public static User from(Request request) {
        return User.of(
                new UserCredentials(request.nickname(), request.password()),
                UserStatus.of(),
                request.name(),
                request.mail()
        );
    }

    public static Response toResponse(User user) {
        return new Response(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getName(),
                user.getMail(),
                user.getUserCredentials().nickname()
        );
    }

    public static ResponseWithOnline toResponse(User user, @Nullable UUID userProfileId, boolean online) {
        return new ResponseWithOnline(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getName(),
                user.getMail(),
                user.getUserCredentials().nickname(),
                userProfileId,
                online
        );
    }

    public static ResponseWithLastActivatedAt toResponseWithLastActivatedAt(User user) {
        return new ResponseWithLastActivatedAt(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getName(),
                user.getMail(),
                user.getUserCredentials().nickname(),
                user.getUserStatus().lastActivatedAt()
        );
    }
}
