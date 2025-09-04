package com.sprint.mission.discodeit.entity;

import java.io.Serializable;

public class Message extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private String senderName;
    private String messageContent;
    private String reciverName;

    public Message(String senderName, String messageContent, String reciverName) {
        super();
        this.senderName = senderName;
        this.messageContent = messageContent;
        this.reciverName = reciverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
        setUpdateAt(System.currentTimeMillis());
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
        setUpdateAt(System.currentTimeMillis());
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
        setUpdateAt(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderName='" + senderName + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", reciverName='" + reciverName + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdateAt() +
                '}';
    }
}
