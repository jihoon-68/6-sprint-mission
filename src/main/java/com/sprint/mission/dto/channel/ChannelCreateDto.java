package com.sprint.mission.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChannelCreateDto {

    private String channelName;
    private String channelDescription;
    private String channelType;
    private List<String> channelMembers;

}
