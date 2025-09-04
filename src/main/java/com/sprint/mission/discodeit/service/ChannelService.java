package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Channel.ChannelInfoDto;
import com.sprint.mission.discodeit.dto.Channel.CreateChannelDto;
import com.sprint.mission.discodeit.dto.Channel.PublicChannelUpdateDto;
import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel createPublicChannel(CreateChannelDto createChannelDto);
    Channel createPrivateChannel(CreateChannelDto createChannelDto);
    ChannelInfoDto find(UUID channelId);
    List<ChannelInfoDto> findAllByUserId(UUID userId);
    Channel update(PublicChannelUpdateDto channelUpdateDto);
    void delete(UUID channelId);
}
