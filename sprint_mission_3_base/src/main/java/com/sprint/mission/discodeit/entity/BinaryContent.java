package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID profileId;
    private UUID messageId;
    private Instant createdAt;
    private String content;

    public BinaryContent(UUID profileId, UUID messageId, String content) {
        this.id = UUID.randomUUID();
        this.profileId = profileId;
        this.messageId = messageId;
        this.createdAt = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        this.content = content;
    }

    public void update(String newcContent) {
        if(newcContent != null && !newcContent.equals(this.content)) {
            this.content = newcContent;
        }
    }
}
