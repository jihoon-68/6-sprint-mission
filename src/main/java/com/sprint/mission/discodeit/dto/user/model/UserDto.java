package com.sprint.mission.discodeit.dto.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID id;
    private String email;
    private String username;
    private List<UUID> friendIds;
    private List<UUID> sentFriendRequests;
    private List<UUID> receivedFriendRequests;
    private String userStatus;
}
