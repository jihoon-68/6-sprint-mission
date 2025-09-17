package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTOs.Message.MessageCreateRequest;
import com.sprint.mission.discodeit.DTOs.Message.UpdateMessageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    Message create(MessageCreateRequest messageCreateRequest);
    Message find(UUID messageId);
    List<Message> findallByChannelId(UUID channelId);

    Message update(UpdateMessageDto updateMessageDto);
    void delete(UUID messageId);
}
