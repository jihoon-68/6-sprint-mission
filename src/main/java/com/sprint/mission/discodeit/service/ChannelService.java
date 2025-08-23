package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    public void createChannel(String channelName, UUID ownerId);
    public void updateChannelName(UUID id, String channelName);
    public void deleteChannelById(UUID id);
    public void deleteChannelByOwnerId(UUID id);
    public List<Channel> findAllChannels();
    public Channel findChannelById(UUID id);
    public List<Channel> findChannelByOwnerId(UUID id);
}
