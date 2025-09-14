package com.sprint.mission.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Message extends EntityCommon{

    private UUID channelId;
    private UUID senderId;
    private String content;
    List<User> attachments;

    public Message(UUID channelId, UUID senderId, String content, List<User> attachments) {

        super();
        this.channelId = channelId;
        this.senderId = senderId;
        this.content = content;
        this.attachments = attachments;

    }

}
