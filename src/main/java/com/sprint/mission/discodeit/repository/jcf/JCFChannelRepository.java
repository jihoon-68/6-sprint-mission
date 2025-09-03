package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {

    Map<UUID, Channel> channels = new HashMap<UUID, Channel>();

    @Override
    public void addChannel(Channel channel) {
        channels.put(channel.getChannelId(),channel);
    }

    @Override
    public Channel readChannel(UUID channelId) {
        return channels.get(channelId);
    }

    @Override
    public void deleteChannel(UUID userId) {
        channels.remove(userId);
    }

    @Override
    public void updateChannel(Channel channel) {
        if(channels.containsKey(channel.getChannelId())) {
            channels.put(channel.getChannelId(),channel);
        }
        else{
            throw new IllegalArgumentException("Channel not found");
        }
    }

    @Override
    public List<Channel> readAllChannel() {
        return new ArrayList<Channel>(channels.values());
    }
}
