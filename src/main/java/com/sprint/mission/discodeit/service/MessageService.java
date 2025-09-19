package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.messagedto.CreateMessage;
import com.sprint.mission.discodeit.dto.messagedto.UpdateMessage;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(CreateMessage createMessage);
    Message find(UUID messageId);
    List<Message> findAllByChannelId(UUID channelId);
    Message update(UUID messageId, UpdateMessage updateMessage);
    void delete(UUID messageId);
}
