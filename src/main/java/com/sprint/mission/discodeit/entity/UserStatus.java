package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.dto.userStatus.model.UserStatusDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class UserStatus implements Serializable {
    private UUID id;
    private UUID userId;
    private Instant updateAt;
    private Instant createAt;
    private Instant lastLogin;
    private boolean isLogin;
    private static final long serializableId = 1L;

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.createAt = Instant.now();
        this.isLogin = false;
    }

    public void updateLastLogin() {
        this.lastLogin = Instant.now();
        this.updateAt = Instant.now();
    }

    public void updateIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
        this.updateAt = Instant.now();
    }

    public static UserStatusDto toDto(UserStatus userStatus) {
        UserStatusDto userStatusDto = new UserStatusDto();
        userStatusDto.setId(userStatus.getId());
        userStatusDto.setUserId(userStatus.getUserId());
        userStatusDto.setLogin(userStatus.isLogin);
        userStatusDto.setLastLogin(userStatus.lastLogin);

        return  userStatusDto;
    }
}
