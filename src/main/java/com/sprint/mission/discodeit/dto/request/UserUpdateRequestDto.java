package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    @NotNull
    private UserUpdateRequest userUpdateRequest;
    
    private Optional<BinaryContentCreateRequest> profileCreateRequest;

}
