package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID senderId;
    private String contents;
    private final UUID recieverId;

    public Message(UUID senderId, String contents, UUID recieverId) {
        super();
        this.senderId = UUID.randomUUID();;
        this.contents = contents;
        this.recieverId = UUID.randomUUID();;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public String getContents() {
        return contents;
    }

    public UUID getRecieverId(){
        return recieverId;
    }


    public void updateMessage(String newContents) {
        boolean contentsChanged = (newContents != null) && !this.contents.equals(newContents);
        if (contentsChanged) {
            this.contents = newContents;
            this.setUpdatedAt();
        } else {
            System.out.println("Contents are not change");
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", Message contents='" + contents + '\'' +
                ", recieverId='" + recieverId + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdateAt() +
                '}';
    }
}
