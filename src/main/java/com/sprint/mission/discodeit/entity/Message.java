package com.sprint.mission.discodeit.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "messages")
@NoArgsConstructor
public class Message extends BaseUpdatableEntity {

    @Column
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Channel channel;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User author;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(
            name = "message_attachments",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<BinaryContent> attachmentIds;


    public Message(User author, Channel channel, String content) {
        this.channel = channel;
        this.author = author;
        this.content = content;
        this.attachmentIds = new ArrayList<>();
    }

    public Message(User author, Channel channel, String content, List<BinaryContent> attachmentIds) {
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
