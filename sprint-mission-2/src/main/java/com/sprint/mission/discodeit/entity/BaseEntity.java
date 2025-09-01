package com.sprint.mission.discodeit.entity;

public abstract class BaseEntity {
    public Long setTime() {
        return System.currentTimeMillis();
    }
}
