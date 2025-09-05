package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Message extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final UUID sender;
    private final UUID channel;
    private final Instant created;


    private String content;
    private Instant updated;

    public Message(UUID sender, UUID channel, String content) {
        this.channel = channel;
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.content = content;
        this.created = setTime();
    }


    public void updateMessage(String text) {
        this.content = text;
        this.updated = setTime();
    }

    public void update(String newContent) {
        boolean anyValueUpdated = false;
        if (newContent != null && !newContent.equals(this.content)) {
            this.content = newContent;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updated = Instant.now();
        }
    }


    public String toString(){
        return "메시지 정보" + "\n" +
                "메시지 ID: " + this.id + "\n" +
                "메시지 발신자 ID: " + this.sender + "\n" +
                "메시지 수신 체널 ID:  " + this.channel + "\n" +
                "메시지 내용: " + this.content + "\n" +
                "메시지 생성일자: " + this.created + "\n" +
                "메시지 수정일자: " + this.updated + "\n";
    }
}
