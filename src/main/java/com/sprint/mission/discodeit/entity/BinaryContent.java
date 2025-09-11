package com.sprint.mission.discodeit.entity;

import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class BinaryContent extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String BINARY_CONTENT_IS_READ_ONLY = "BinaryContent is read-only";

    private final OwnerType ownerType;
    private final UUID ownerId;
    private final byte[] data;

    public enum OwnerType {USER_PROFILE, MESSAGE_ATTACHMENT,}

    public BinaryContent(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            OwnerType ownerType,
            UUID ownerId,
            byte[] data
    ) {
        super(id, createdAt, null);
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.data = Arrays.copyOf(data, data.length);
    }

    public static BinaryContent ofUserProfile(UUID ownerId, byte[] data) {
        return of(OwnerType.USER_PROFILE, ownerId, data);
    }

    public static BinaryContent ofMessageAttachment(UUID ownerId, byte[] data) {
        return of(OwnerType.MESSAGE_ATTACHMENT, ownerId, data);
    }

    private static BinaryContent of(OwnerType ownerType, UUID ownerId, byte[] data) {
        return new BinaryContent(
                null,
                null,
                ownerType,
                ownerId,
                data
        );
    }

    @Override
    public void setUpdatedAt(Instant updatedAt) {
        throw new UnsupportedOperationException(BINARY_CONTENT_IS_READ_ONLY);
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public String toString() {
        return "BinaryContent{" +
                "ownerType=" + ownerType +
                ", ownerId=" + ownerId +
                ", data=" + Arrays.toString(data) +
                "} " + super.toString();
    }
}
