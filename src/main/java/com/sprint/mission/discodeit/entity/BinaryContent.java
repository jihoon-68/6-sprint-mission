package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private Instant createdAt;


}
