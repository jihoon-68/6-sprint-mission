package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.DTO.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.Enum.UserStatusType;
import lombok.Getter;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final UUID userId;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant lastAccessAt;
    private UserStatusType accessType;

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.accessType = UserStatusType.OFFLINE;
        this.userId = userId;
        this.lastAccessAt = Instant.now();
    }

    public void update(UpdateUserStatusDTO updateUserStatusDTO) {
        boolean anyValueUpdated = false;

        if (updateUserStatusDTO.lastActiveAt() != null && !updateUserStatusDTO.lastActiveAt().equals(this.lastAccessAt)){
            this.lastAccessAt = updateUserStatusDTO.lastActiveAt();
            anyValueUpdated = true;
        }

        if (updateUserStatusDTO.online() != this.accessType.getValue()){
            this.accessType = updateUserStatusDTO.online()?UserStatusType.ONLINE:UserStatusType.OFFLINE;
            anyValueUpdated = true;
        }

        if(anyValueUpdated){
            this.updatedAt = Instant.now();
        }
    }

    public void connect() {
        this.lastAccessAt = Instant.now();
        this.accessType = UserStatusType.ONLINE;
        this.updatedAt = Instant.now();
    }

    public void disconnect() {
        this.accessType = UserStatusType.OFFLINE;
        this.updatedAt = Instant.now();
    }

    public void isConnecting(Instant newLastAccessAt) {
        long duration = newLastAccessAt.toEpochMilli() - this.lastAccessAt.toEpochMilli();
        if (duration <= 300000) {
            this.lastAccessAt = Instant.now();
            this.accessType = UserStatusType.ONLINE;
            return;
        }
        this.accessType = UserStatusType.OFFLINE;
    }
}
