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
    private Instant LastAccessAt;
    private UserStatusType accessType;

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.accessType = UserStatusType.OFFLINE;
        this.userId = userId;
        this.LastAccessAt = Instant.now();
    }

    public void update(UpdateUserStatusDTO updateUserStatusDTO) {
        boolean anyValueUpdated = false;

        if (updateUserStatusDTO.LastAccessAt() != null && !updateUserStatusDTO.LastAccessAt().equals(this.LastAccessAt)){
            this.LastAccessAt = updateUserStatusDTO.LastAccessAt();
            anyValueUpdated = true;
        }

        if (updateUserStatusDTO.AccessType() != null && updateUserStatusDTO.AccessType().getValue() != this.accessType.getValue()){
            this.accessType = updateUserStatusDTO.AccessType();
            anyValueUpdated = true;
        }

        if(anyValueUpdated){
            this.updatedAt = Instant.now();
        }
    }

    public UserStatusType isConnecting() {
        Duration duration = Duration.between(this.LastAccessAt, Instant.now());
        if (duration.toSeconds() >= 300) {
            LastAccessAt = Instant.now();
            return UserStatusType.ONLINE;
        }
        return UserStatusType.OFFLINE;
    }
}
