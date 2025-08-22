package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends Common{
    private String messageContext;
    private final UUID sender;
    private final UUID receiver;

    public Message(String messageContext, UUID sender, UUID receiver) {
        super();
        this.messageContext = messageContext;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessageContext() {return messageContext;}
    public void setMessageContext(String messageContext) {this.messageContext = messageContext;}
    public UUID getSender() {return sender;}
    public UUID getReceiver() {return receiver;}

    @Override
    public String toString() {
        return "Message{" +
                "messageContext='" + messageContext + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", uuid=" + getUuid() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
