package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends Common {


    protected String message;

    public Message(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void updateMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", id=" + id +
                ", createAt=" + createAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
