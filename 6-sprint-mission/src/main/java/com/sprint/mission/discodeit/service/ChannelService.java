package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel read(String name);
    Channel create(String name);
    List<Channel> allRead();
    Channel modify(UUID id);
    Channel delete(UUID id);
}
