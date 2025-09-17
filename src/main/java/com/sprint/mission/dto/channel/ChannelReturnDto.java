package com.sprint.mission.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ChannelReturnDto {

    private String channelName;
    private String channelDescription;
    private String channelType;
    private List<UUID> channelMembers;
    private Instant lastMessageTime;
}
