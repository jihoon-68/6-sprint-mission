package com.sprint.mission.discodeit.entity;

public class Message extends Common {
    private User author;
    private String content;
    private Channel channel;

    public Message(User author, String content, Channel channel) {
        this.author = author;
        this.content = content;
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void update(String content) {
        if (content != null) this.content = content;
        super.updatedAt();
    }
}
