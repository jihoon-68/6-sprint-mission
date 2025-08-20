package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private String content;
    private String sender;
    private String reciever;
    private int length;
    private UUID channelId;
    private Common common;
    private Channel channel;

    public Message(User sender, User reciever, String content, Channel channel) {
        this.sender = sender.getName();
        this.reciever = reciever.getName();
        this.content = content;
        this.length = content.length();
        this.common = new Common();
        this.channel = channel;
        this.channelId = channel.getCommon().getId();
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public Common getCommon() {
        return common;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", reciever='" + reciever + '\'' +
                ", length=" + length +
                ", channelId=" + channelId +
                ", messageId=" + common.getId() +
                '}';
    }
}
