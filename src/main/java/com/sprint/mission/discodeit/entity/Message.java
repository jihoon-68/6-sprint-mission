package com.sprint.mission.discodeit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "messages")
public class Message extends BaseUpdatableEntity {

    @Column
    private String content;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Column(nullable = false)
    @JoinColumn(name = "channel_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Channel channel;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User author;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinTable(
            name = "message_attachments",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<BinaryContent> attachmentIds;



    public Message(User author, Channel channel, String content) {
        super();
        this.channel = channel;
        this.author = author;
        this.content = content;
        this.attachmentIds = new ArrayList<>();
    }

    public Message(User author, Channel channel, String content, List<BinaryContent> attachmentIds) {
        super();
        this.channel = channel;
        this.author = author;
        this.content = content;
        this.attachmentIds = new ArrayList<>(attachmentIds);
    }

    public void update(String content) {
        boolean anyValueUpdated = false;
        if (content != null && !content.equals(this.content)) {
            this.content = content;
            anyValueUpdated = true;
        }
        if (anyValueUpdated) {
            this.updatedAtNow();
        }
    }

}
