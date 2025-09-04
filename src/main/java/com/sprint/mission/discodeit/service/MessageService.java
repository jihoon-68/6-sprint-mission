package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Message.CreateMessageDto;
import com.sprint.mission.discodeit.dto.Message.UpdateMessageDto;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(CreateMessageDto createMessageDto);
    Message find(UUID messageId);
    List<Message> findallByChannelId(UUID channelId);
    Message update(UpdateMessageDto updateMessageDto);
    void delete(UUID messageId);
}
