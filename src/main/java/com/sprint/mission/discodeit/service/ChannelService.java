package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.Channel.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.FindChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.UpdateChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel createPublic(CreatePublicChannelDTO createPublicChannelDTO);
    Channel createPrivate(CreatePrivateChannelDTO createPrivateChannelDTO);
    FindChannelDTO find(UUID id);
    List<FindChannelDTO> findAllByUserId(UUID userId);
    Channel update(UUID channelID,UpdateChannelDTO updateChannelDTO);
    void delete(UUID id);
}
