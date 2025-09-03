package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface MessageService {
    Message create(String content, UUID user, UUID channel, Message.MessageType type);
    Optional<Message> readId(UUID id);
    List<Message> readUser(User user);
    List<Message> readChannel(Channel channel);
    List<Message> readContent(String content);
    List<Message> readAll();
    boolean update(UUID id, String content);
    boolean delete(UUID id);
}
