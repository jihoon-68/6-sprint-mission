package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant createdAt;
    private Instant updatedAt;
    private String content;
    private final UUID channelId;
    private final UUID authorId;
    private final List<UUID> attachmentIds;



    public Message(UUID authorId, UUID channelId, String content) {
        this.channelId = channelId;
        this.id = UUID.randomUUID();
        this.authorId = authorId;
        this.content = content;
        this.attachmentIds = new ArrayList<>();

        this.createdAt = Instant.now();
    }

    public Message(UUID authorId, UUID channelId, String content, List<UUID> attachmentIds) {
        this.channelId = channelId;
        this.id = UUID.randomUUID();
        this.authorId = authorId;
        this.content = content;
        this.attachmentIds = new ArrayList<>(attachmentIds);
        this.createdAt = Instant.now();
    }

    public void update(String content) {
        boolean anyValueUpdated = false;
        if (content != null && !content.equals(this.content)) {
            this.content = content;
            anyValueUpdated = true;
        }
        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }


    public String toString() {
        return "메시지 정보" + "\n" +
                "메시지 ID: " + this.id + "\n" +
                "메시지 발신자 ID: " + this.authorId + "\n" +
                "메시지 수신 체널 ID:  " + this.channelId + "\n" +
                "메시지 내용: " + this.content + "\n" +
                "메시지 생성일자: " + this.createdAt + "\n" +
                "메시지 수정일자: " + this.updatedAt + "\n";
    }
}
