package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public class BinaryContent {
    private UUID id;
    private UUID userId;
    private UUID messageId;
    private String imagePath;

    // User용 생성자
    public BinaryContent(UUID userId, String imagePath) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.imagePath = imagePath;
    }
    // Message용 생성자
    public BinaryContent(UUID messageId, String imagePath, boolean isMessage) {
        this.id = UUID.randomUUID();
        this.messageId = messageId;
        this.imagePath = imagePath;
    }
}
