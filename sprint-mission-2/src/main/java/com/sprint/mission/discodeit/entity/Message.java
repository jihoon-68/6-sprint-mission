package com.sprint.mission.discodeit.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Message {
    private UUID id;
    private User sender;
    private String text;
    private Long created;
    private Long updated;

    public Message(User sender, String text) {
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.text = text;
        this.created = System.currentTimeMillis();
        this.updated = System.currentTimeMillis();
    }

}
