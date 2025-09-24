package com.sprint.mission.discodeit.channel.service;

import com.sprint.mission.discodeit.channel.ChannelDto.Request;
import com.sprint.mission.discodeit.channel.ChannelDto.Request.PublicRequest;
import com.sprint.mission.discodeit.channel.ChannelDto.Response;
import com.sprint.mission.discodeit.channel.ChannelDto.Response.PublicResponse;

import java.util.Set;
import java.util.UUID;

public interface ChannelService {

    Response createChannel(Request request);

    Response getChannelByChannelTypeAndId(String type, UUID id);

    Set<Response> getChannelsByChannelType(String type);

    PublicResponse updateChannelById(UUID id, PublicRequest request);

    void deleteChannelById(UUID id);
}
