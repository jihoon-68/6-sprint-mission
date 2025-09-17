package com.sprint.mission.dto.readstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ReadStatusCreateDto {

    private UUID userId;
    private UUID channelId;

}
