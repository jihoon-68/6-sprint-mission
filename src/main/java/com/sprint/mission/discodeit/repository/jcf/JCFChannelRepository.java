package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFChannelRepository implements ChannelRepository
{
    private final Map<UUID, Channel> channels;
    public JCFChannelRepository()
    {
        channels =  new HashMap<UUID, Channel>();
    }

    @Override
    public boolean save(Channel channel) {

        if(channel == null)
        {
            System.out.println("Channel is null");
            return false;
        }

        if(channels.containsKey(channel.getId()))
        {
            System.out.println("Channel name exsists");
            return false;
        }

        channels.put(channel.getId(), channel);
        return true;
    }

    @Override
    public Map<UUID, Channel> getAllChannels() {
        return new HashMap<>(channels);
    }

    @Override
    public boolean delete(UUID channelId) {
        if(channels.containsKey(channelId) == false)
        {
            System.out.println("Channel with id " + channelId + " does not exist");
            return false;
        }

        channels.remove(channelId);
        return true;
    }

    @Override
    public boolean update(Channel channel) {
        if(channels.containsKey(channel.getId()) == false)
        {
            System.out.println("Channel with id " + channel.getId() + " does not exist");
            return false;
        }
        channel.updateUpdatedAt();

        channels.put(channel.getId(),channel);
        return true;
    }

}
