package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;

public interface ChannelService {
    Channel read(String name);
    Channel create(String name);
    List<Channel> allRead();
    void modify(String name);
    void delete(String name);
}
