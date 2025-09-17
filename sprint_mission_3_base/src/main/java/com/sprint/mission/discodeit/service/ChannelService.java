package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.Channel.ChannelResponse;
import com.sprint.mission.discodeit.DTO.Channel.CreateChannelRequest;
import com.sprint.mission.discodeit.DTO.Channel.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel createPublicChannel (CreateChannelRequest request);
    Channel createPrivateChannel (CreateChannelRequest request);
    ChannelResponse find(UUID channelId);
    List<ChannelResponse> findAllByUserId(UUID userId);
    Channel update(UpdateChannelRequest request);
    void delete(UUID channelId);
}
