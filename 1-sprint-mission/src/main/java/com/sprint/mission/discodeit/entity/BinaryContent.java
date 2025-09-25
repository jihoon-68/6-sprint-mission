package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent extends Common {
    private static final long serialVersionUID = 1L;

    private UUID profileId;
    private UUID messageId;
    private String attatchmentUrl;

    public BinaryContent(UUID profileId, UUID messageId, String attatchmentUrl) {
        this.profileId = profileId;
        this.messageId = messageId;
        this.attatchmentUrl = attatchmentUrl;
    }

    public void changeProfileImage(String attatchmentUrl) {
        if (attatchmentUrl != null && !attatchmentUrl.equals(this.attatchmentUrl)) {
            this.attatchmentUrl = attatchmentUrl;
        }
    }
}
