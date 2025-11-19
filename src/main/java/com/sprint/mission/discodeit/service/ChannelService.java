package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Channel.*;
import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    ChannelDto createPublic(PublicChannelCreateRequest request);
    ChannelDto createPrivate(PrivateChannelCreateRequest request);
    List<ChannelDto> findAllByUserId(UUID userId);
    ChannelDto update(UUID channelID,PublicChannelUpdateRequest request);
    void delete(UUID id);
}
