package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends Common{
    private String messageContext;
    private final UUID sender;
    private final UUID receiver;

    public Message(String messageContext, UUID sender, UUID receiver) {
        super();
        if(messageContext == null || messageContext.trim().isEmpty()) {
            throw new IllegalArgumentException("메시지 내용은 비어 있을 수 없습니다.");
        }
        if(sender == null) {
            throw new IllegalArgumentException("보내는 사람은 null 일 수 없습니다.");
        }
        if(receiver == null) {
            throw new IllegalArgumentException("받는 사람은 null 일 수 없습니다.");
        }
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
