package com.sprint.mission.service;

import com.sprint.mission.dto.message.MessageCreateDto;
import com.sprint.mission.dto.message.MessageUpdateDto;
import com.sprint.mission.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    Message create(MessageCreateDto messageCreateDto);
    List<Message> findAllByChannelId(UUID channelId);
    void update(MessageUpdateDto messageUpdateDto);
    void delete(UUID id);

}
