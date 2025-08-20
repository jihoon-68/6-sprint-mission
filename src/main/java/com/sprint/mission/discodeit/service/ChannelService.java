package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.UUID;

public interface ChannelService {
    void createChannel(String ChName);
    Channel getChannel(UUID id);
    List<Channel> getChannels();
    void updateChannel(UUID id, String newChName);
    void deleteChannel(UUID id);
}
