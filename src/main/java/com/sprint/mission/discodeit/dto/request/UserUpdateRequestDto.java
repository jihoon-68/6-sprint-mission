package com.sprint.mission.discodeit.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class UserUpdateRequestDto {

    private UUID userId;
    private UserUpdateRequest userUpdateRequest;
    private Optional<BinaryContentCreateRequest> profileCreateRequest;

    public UserUpdateRequestDto(UUID userId, UserUpdateRequest userUpdateRequest, Optional<BinaryContentCreateRequest> profileCreateRequest) {
        this.userId = userId;
        this.userUpdateRequest = userUpdateRequest;
        this.profileCreateRequest = profileCreateRequest;
    }

}
