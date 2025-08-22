package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(String name, List<User> users, List<Message> messages);
    Channel findById(UUID id);
    List<Channel> findAll();
    Channel update(UUID id, String name, String topic);
    boolean delete(UUID id);
}