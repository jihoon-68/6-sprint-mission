package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public interface MessageService {
    Message read(String sender);
    Message create(User sender, User reciever, String content, Channel channel);
    List<Message> allRead();
    Message modify(String message);
    Message delete(String message);
}
