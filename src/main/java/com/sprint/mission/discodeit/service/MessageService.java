package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    public void createMessage(UUID authorId, UUID channelId, String content);
    public void updateContent(String content, UUID id);
    public void deleteMessage(UUID id);
    public List<Message> findAllMessages();
    public Message findMessageById(UUID id);
    public List<Message> findMessagesByAuthorIdAndChannelId(UUID authorId, UUID channelId);
    public List<Message> findMessagesByChannelId(UUID channelId);
}
