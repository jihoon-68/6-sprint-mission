package com.sprint.mission.dto.user;

import com.sprint.mission.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserReturnDto {
    private UUID id;
    private String username;
    private String email;
    private UUID profileId;
    private UserStatus status;
}
