package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.Enum.ChannelType;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Channel extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant created;
    private final ChannelType type;

    private String name;
    private Instant updated;
    private String description;

    public Channel(String name, String description , ChannelType type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.created = setTime();
        this.description = description;
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
            this.updated = setTime();
        }
    }

    @Override
    public String toString(){
        return "체널 정보: " + "\n" +
                "체널 ID: " + this.id + "\n" +
                "체널 이름: " + this.name + "\n" +
                "체널 생성일자: " + this.name + "\n" +
                "체널 수정일자: " + this.created + "\n";

    }

}
