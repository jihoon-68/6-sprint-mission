package com.sprint.mission.discodeit.entity;
/**
 * 객체 클래스 Message
 * content: String
 * user: User
 * channel: Channel
 * type: MessageType(TEXT, VIDEO, IMAGE, FILE)
 */
public class Message extends Common{
    private String content;
    private User user;
    private Channel channel;
    private MessageType type;

    // Constructor
    public Message(String content, User user, Channel channel, MessageType type) {
        super();
        this.content = content;
        this.user = user;
        this.channel = channel;
        this.type = type;
    }

    // Getter
    public String getContent() { return content; }
    public User getUser() { return user; }
    public Channel getChannel() { return channel; }
    public MessageType getType() { return type; }

    // Setter
    public void setContent(String content) { this.content = content; }
    public void setUser(User user) { this.user = user; }
    public void setChannel(Channel channel) { this.channel = channel; }
    public void setType(MessageType type) { this.type = type; }

    // MessageType enum
    public enum MessageType {
        TEXT,
        VIDEO,
        IMAGE,
        FILE
    }

    // Update
    public void update(String content) { this.content = content != null ? content : this.content; }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + getId() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                ", content=" + content +
                ", user=" + user +
                ", channel=" + channel +
                ", type=" + type +
                '}';
    }
}
