package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public interface MessageService {
    Message createMessage(User creator, Channel channel, String content);
    void editMessage(User user, Message message, String content);
    void deleteMessage(User user, Message message);
}
