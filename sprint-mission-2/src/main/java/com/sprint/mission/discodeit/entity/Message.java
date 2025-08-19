package com.sprint.mission.discodeit.entity;


import java.util.UUID;

public class Message {
    private final UUID id;
    private final User sender;
    private String text;
    private final Long created;
    private Long updated;

    public Message(User sender, String text) {
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.text = text;
        this.created = System.currentTimeMillis();
    }

    public UUID getMessageId() {return this.id;}
    public User getMessageSender() {return this.sender;}
    public String getMessageText() {return this.text;}
    public Long getMessageCreated() {return this.created;}
    public Long getMessageUpdated() {return this.updated;}

    public void updateMessage(String text) {
        this.text = this.text;
        this.updated = System.currentTimeMillis();
    }
}
