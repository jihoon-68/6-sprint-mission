package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.*;

public interface MessageService {
    Message create(User author, String content, Channel channel);
    Message findById(UUID id);
    List<Message> findAll();
    Message update(UUID id, String content);
    boolean delete(UUID id);
}