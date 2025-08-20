package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;
import java.util.*;

public interface MessageService {
    Message create(Message message);
    Message findById(String id);
    List<Message> findAll();
    List<Message> findByChannelId(String channelId); // 편의
    Message update(String id, String content);
    boolean delete(String id);
}