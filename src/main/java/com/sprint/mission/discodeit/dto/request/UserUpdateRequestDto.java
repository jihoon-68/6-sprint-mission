package com.sprint.mission.discodeit.dto.request;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    private UserUpdateRequest userUpdateRequest;
    private Optional<BinaryContentCreateRequest> profileCreateRequest;

}
