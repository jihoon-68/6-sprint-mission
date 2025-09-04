package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2L;
    private final UUID id;
    private final User sender;
    private String text;
    private final Long created;
    private Long updated;

    public Message(User sender, String text) {
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.text = text;
        this.created = setTime();
    }

    public UUID getMessageId() {return this.id;}
    public User getMessageSender() {return this.sender;}
    public String getMessageText() {return this.text;}
    public Long getMessageCreated() {return this.created;}
    public Long getMessageUpdated() {return this.updated;}

    public void updateMessage(String text) {
        this.text = text;
        this.updated = setTime();
    }

    public String toString(){
        return "메시지 정보" + "\n" +
                "메시지 ID: " + this.id + "\n" +
                "메시지 발신자: " + this.sender.getUsername() + "\n" +
                "메시지 내용: " + this.text + "\n" +
                "메시지 생성일자: " + this.created + "\n" +
                "메시지 수정일자: " + this.updated + "\n";
    }
}
