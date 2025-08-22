package com.sprint.mission.discodeit.entity;

import java.io.Serializable;

public class Message extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public void update(String content) {
        if (content != null) this.content = content;
        super.updatedAt();
    }
}
