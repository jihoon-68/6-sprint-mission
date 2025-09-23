package com.sprint.mission.discodeit.entity;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
import lombok.Getter;


import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;

@Getter
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final UUID sender;
    private final UUID channel;
    private final Instant created;

    private List<UUID> attachmentIds;
    private String content;
    private Instant updated;

    public Message(UUID sender, UUID channel, String content) {
        this.channel = channel;
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.content = content;
        this.attachmentIds = new ArrayList<>();

        this.created = Instant.now();
    }

    public Message(UUID sender, UUID channel, String content,List<UUID> attachmentIds) {
        this.channel = channel;
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.content = content;
        this.attachmentIds = new ArrayList<>(attachmentIds);
        this.created = Instant.now();
    }

    public void update(String content, List<UUID> attachmentIds) {
        boolean anyValueUpdated = false;
        if (content != null && !content.equals(this.content)){
            this.content = content;
            anyValueUpdated = true;
        }

        if (attachmentIds != null && !attachmentIds.isEmpty() && !attachmentIds.equals(this.attachmentIds)) {
            this.attachmentIds = new ArrayList<>(attachmentIds);

            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updated = Instant.now();
        }
    }


    public String toString(){
        return "메시지 정보" + "\n" +
                "메시지 ID: " + this.id + "\n" +
                "메시지 발신자 ID: " + this.sender + "\n" +
                "메시지 수신 체널 ID:  " + this.channel + "\n" +
                "메시지 내용: " + this.content + "\n" +
                "메시지 생성일자: " + this.created + "\n" +
                "메시지 수정일자: " + this.updated + "\n";
    }
}
