package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID profileId;
    private UUID messageId;
    private Instant createdAt;
    private String attatchmentUrl;

    public BinaryContent(UUID profileId, UUID messageId, String attatchmentUrl)
    {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.profileId = profileId;
        this.messageId = messageId;
        this.attatchmentUrl = attatchmentUrl;
    }

    public void changeProfileImage(String attatchmentUrl)
    {
        if (attatchmentUrl != null && !attatchmentUrl.equals(this.attatchmentUrl)) {
            this.attatchmentUrl = attatchmentUrl;
        }
    }
}
