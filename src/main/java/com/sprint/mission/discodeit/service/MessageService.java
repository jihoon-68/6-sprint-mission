package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    void createMessage(UUID authorId, UUID channelId, UUID receiverId, String content, boolean isDrawnReceiver);
    void updateContent(String content, UUID id);
    void deleteMessageById(UUID id);
    List<Message> findAllMessages();
    Message findMessageById(UUID id);
    List<Message> findMessagesByAuthorIdAndChannelId(UUID authorId, UUID channelId);
    List<Message> findMessagesByChannelId(UUID channelId);
    void deleteMessageByAuthorId(UUID authorId);
    List<Message> findFriendConversation(UUID userId, UUID friendId);
    List<Message> findMyMessagesToFriend(UUID userId, UUID friendId);
    void deleteMessageByChannelIds(List<UUID> channelIds);
    void cleanupDM();
    void anonymizeUserMessage(UUID userId);
}
