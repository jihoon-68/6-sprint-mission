package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelResponse;
import com.sprint.mission.discodeit.dto.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.UpdateChannelRequest;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    ChannelResponse createPublicChannel(CreatePublicChannelRequest request);

    ChannelResponse createPrivateChannel(CreatePrivateChannelRequest request);

    ChannelResponse find(UUID channelId);

    List<ChannelResponse> findAllByUserId(UUID userId);

    ChannelResponse update(UUID channelId, UpdateChannelRequest request);

    void delete(UUID channelId);
}
