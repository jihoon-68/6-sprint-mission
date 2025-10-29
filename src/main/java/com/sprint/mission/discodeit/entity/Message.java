package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseUpdatableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false, columnDefinition = "BINARY(16)")
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, columnDefinition = "BINARY(16)")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "message_attachments",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<BinaryContent> attachments = new ArrayList<>();

    public Message(String content, Channel channel, User author, List<BinaryContent> attachments) {
        this.content = content;
        this.channel = channel;
        this.author = author;
        if (attachments != null) {
            this.attachments.addAll(attachments);
        }
    }

    public void update(String newContent) {
        if (newContent != null) {
            this.content = newContent;
        }
    }

    public void addAttachment(BinaryContent attachment) {
        this.attachments.add(attachment);
    }

    @Override
    public UUID getId() {
        return id;
    }
}
