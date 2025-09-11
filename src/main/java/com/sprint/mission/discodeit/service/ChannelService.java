package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.model.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.response.ChannelFindAllResponse;

import java.util.UUID;

public interface ChannelService {
    void createChannel(ChannelCreateRequest request);
    void updateChannelName(ChannelUpdateRequest request);
    void deleteChannelById(UUID id);
    void deleteChannelByOwnerId(UUID id);
    ChannelFindAllResponse findAllByUserId(UUID id);
    ChannelFindAllResponse findAllByOwnerId(UUID id);
    ChannelDto findByChannelId(UUID id);
}
