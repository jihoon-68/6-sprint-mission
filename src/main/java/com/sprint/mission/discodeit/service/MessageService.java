package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(CreateMessageDTO createMessageDTO);
    Message find(UUID id);
    List<Message> findAllByChannelId(UUID channelId);
    void update(UpdateMessageDTO updateMessageDTO);
    void delete(UUID id);
}
