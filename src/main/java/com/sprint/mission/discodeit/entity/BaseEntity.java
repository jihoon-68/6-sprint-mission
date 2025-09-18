package com.sprint.mission.discodeit.entity;

<<<<<<< HEAD
import java.time.Instant;

public abstract class BaseEntity {
    public Instant setTime() {
        return Instant.now();
=======
public abstract class BaseEntity {
    public Long setTime() {
        return System.currentTimeMillis();
>>>>>>> 박지훈
    }
}
