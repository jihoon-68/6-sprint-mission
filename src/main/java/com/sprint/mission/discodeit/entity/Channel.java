package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.Enum.ChannelType;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Channel implements Serializable{
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant createdAt;
    private Instant updatedAt;

    private final ChannelType type;
    private String name;
    private String description;

    public Channel(String channelName, String description) {
        this.id = UUID.randomUUID();
        this.name = channelName;
        this.type = ChannelType.PUBLIC;
        this.createdAt = Instant.now();
        this.description = description;
    }

    public Channel() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.type = ChannelType.PRIVATE;
        this.name = "";
        this.description = "";
    }



    public void update(String newName, String newDescription) {
        boolean anyValueUpdated = false;
        if (newName != null && !newName.equals(this.name)) {
            this.name = newName;
            anyValueUpdated = true;
        }
        if (newDescription != null && !newDescription.equals(this.description)) {
            this.description = newDescription;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }

    @Override
    public String toString(){
        return "체널 정보: " + "\n" +
                "체널 ID: " + this.id + "\n" +
                "체널 이름: " + this.name + "\n" +
                "체널 생성일자: " + this.name + "\n" +
                "체널 수정일자: " + this.createdAt + "\n";

    }

}
