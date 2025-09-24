package com.sprint.mission.discodeit.binarycontent.domain;

import com.sprint.mission.discodeit.binarycontent.domain.BinaryContentException.EmptyBinaryContentException;
import com.sprint.mission.discodeit.common.persistence.BaseEntity;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class BinaryContent extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2L;

    private final OwnerType ownerType;
    private final UUID ownerId;
    private final byte[] bytes;

    public enum OwnerType {USER_PROFILE, MESSAGE_ATTACHMENT,}

    private BinaryContent(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            OwnerType ownerType,
            UUID ownerId,
            byte[] bytes
    ) {
        super(id, createdAt, null);
        if (bytes.length == 0) {
            throw new EmptyBinaryContentException();
        }
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.bytes = Arrays.copyOf(bytes, bytes.length);
    }

    public static BinaryContent of(OwnerType ownerType, UUID ownerId, byte[] bytes) {
        return new BinaryContent(
                null,
                null,
                ownerType,
                ownerId,
                bytes
        );
    }

    @Override
    public void setUpdatedAt(Instant updatedAt) {
        //  no-op: BinaryContent is read-only
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public byte[] getBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }

    @Override
    public String toString() {
        return "BinaryContent{" +
                "ownerType=" + ownerType +
                ", ownerId=" + ownerId +
                ", bytes=" + Arrays.toString(bytes) +
                "} " + super.toString();
    }
}
