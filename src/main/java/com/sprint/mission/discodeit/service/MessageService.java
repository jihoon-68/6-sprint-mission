package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageDto;

import java.util.Set;
import java.util.UUID;

public interface MessageService {

    MessageDto.Response create(MessageDto.Request request);

    Set<MessageDto.Response> readAll(UUID channelId);

    MessageDto.Response update(UUID id, MessageDto.Request request);

    void delete(UUID id);
}
