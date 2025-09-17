package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTOs.Channel.ChannelUpdate;
import com.sprint.mission.discodeit.DTOs.Channel.ChannelView;
import com.sprint.mission.discodeit.DTOs.Channel.PrivateChannelCreate;
import com.sprint.mission.discodeit.DTOs.Channel.PublicChannel;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(PrivateChannelCreate channel);
    Channel create(PublicChannel channel);
    ChannelView find(UUID channelId);
    List<ChannelView> findAllByUserId(UUID id);
    Channel update(ChannelUpdate update);
    void delete(UUID channelId);
}