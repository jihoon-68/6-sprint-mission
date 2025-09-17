package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.PrivateChannelDto;
import com.sprint.mission.discodeit.dto.PublicChannelDto;

import java.util.Set;
import java.util.UUID;

public interface ChannelService {

    PublicChannelDto.Response create(PublicChannelDto.Request request);

    PrivateChannelDto.Response create(PrivateChannelDto.Request request);

    PublicChannelDto.MessageInfoResponse readPublic(UUID id);

    PrivateChannelDto.Response readPrivate(UUID id);

    Set<PublicChannelDto.MessageInfoResponse> readAllPublic();

    Set<PrivateChannelDto.Response> readAllPrivate();

    PublicChannelDto.Response update(UUID id, PublicChannelDto.Request request);

    void delete(UUID id);
}
