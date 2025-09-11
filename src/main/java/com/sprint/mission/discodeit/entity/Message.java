package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    private final UUID id;
    private final UUID userId;
    private final UUID channelId;
    private final Instant createdAt;

    private Instant updatedAt;
    private String content; // 내용
    private List<BinaryContent> binaryContents = new ArrayList<>(); // null 허용

    public Message(UUID userId, UUID channelId, String content){
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.userId = userId;
        this.channelId = channelId;
        this.content = content;
    }

    // Setter

    public void setContent(String content) { // 메시지 내용 수정
        this.content = content;
        this.updatedAt = Instant.now();
    }

    public void setBinaryContents(List<BinaryContent> binaryContents) { // 메시지 첨부파일 수정
        this.binaryContents = (binaryContents != null ? binaryContents : new ArrayList<>());
        this.updatedAt = Instant.now();
    }


    // toString()
    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                ", channelId=" + channelId +
                ", updatedAt=" + updatedAt +
                ", content='" + content + '\'' +
                '}';
    }
}
