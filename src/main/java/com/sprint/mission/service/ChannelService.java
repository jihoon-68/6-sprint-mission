package com.sprint.mission.service;

import com.sprint.mission.dto.channel.ChannelCreateDto;
import com.sprint.mission.dto.channel.ChannelReturnDto;
import com.sprint.mission.dto.channel.ChannelUpdateDto;
import com.sprint.mission.entity.Channel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ChannelService {

    Channel createPublic(ChannelCreateDto channelCreateDto);
    Channel createPrivate(ChannelCreateDto channelCreateDto);
    Channel find(UUID id);
    Map<String, List<ChannelReturnDto>> findAllByUserId(UUID userId);
    void update(UUID id, ChannelUpdateDto channelUpdateDto);
    void delete(UUID id);

}
