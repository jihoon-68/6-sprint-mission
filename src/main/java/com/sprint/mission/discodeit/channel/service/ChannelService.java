package com.sprint.mission.discodeit.channel.service;

import com.sprint.mission.discodeit.channel.ChannelDto.Request;
import com.sprint.mission.discodeit.channel.ChannelDto.Request.PublicRequest;
import com.sprint.mission.discodeit.channel.ChannelDto.Response;
import com.sprint.mission.discodeit.channel.ChannelDto.Response.PublicResponse;
import com.sprint.mission.discodeit.channel.domain.Channel.ChannelType;

import java.util.Set;
import java.util.UUID;

public interface ChannelService {

    Response createChannel(Request request);

    Response getChannelByChannelTypeAndId(ChannelType channelType, UUID id);

    Set<Response> getChannelsByChannelType(ChannelType channelType);

    PublicResponse updateChannelById(UUID id, PublicRequest request);

    void deleteChannelById(UUID id);
}
