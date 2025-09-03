package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public interface ChannelService {
    public boolean createChannel(String channelName);
    public List<Channel> getChannels();
    public Channel getChannel(String channelName);
    public boolean updateChannel(Channel channel);
    public boolean deleteChannel(Channel channel);
    public void deleteAll();

}
