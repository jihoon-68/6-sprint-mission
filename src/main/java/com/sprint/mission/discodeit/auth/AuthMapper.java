package com.sprint.mission.discodeit.auth;

import com.sprint.mission.discodeit.auth.AuthDto.Response;
import com.sprint.mission.discodeit.user.UserDto;

public final class AuthMapper {

    private AuthMapper() {
    }

    public static Response toResponse(UserDto.ResponseWithLastActivatedAt userResponse) {
        return new Response(
                userResponse.id(),
                userResponse.createdAt(),
                userResponse.updatedAt(),
                userResponse.nickname(),
                userResponse.lastActivatedAt()
        );
    }
}
