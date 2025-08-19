package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class JCFChannelService implements ChannelService {

    private final Map<UUID,Channel> channels;

    public  JCFChannelService()
    {
        channels = new HashMap<UUID, Channel>();
    }

    @Override
    public boolean createChannel(String channelName) {
        if(channelName.isEmpty())
        {
            System.out.println("Channel name is empty");
            return false;
        }

        if(channels.containsKey(channelName))
        {
            System.out.println("Channel with name " + channelName + " already exists");
            return false;
        }

        Channel chan = new Channel(channelName);
        channels.put(chan.getId(),chan);

        return true;
    }

    @Override
    public List<Channel> getChannels() {
        return channels.entrySet()
                .stream()
                .map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public Channel getChannel(String channelName) {

        if(channelName.isEmpty())
        {
            System.out.println("channel name is empty");
            return null;
        }

        Optional<Channel> temp = channels.entrySet().stream()
                .filter(x -> x.getValue().getChannelName().equals(channelName))
                .map(x -> x.getValue())
                .findFirst();

        return temp.orElse(null);
    }

    @Override
    public boolean updateChannel(Channel channel) {

        if(channel == null)
        {
            System.out.println("Channel is empty");
            return false;
        }

        if(channels.containsKey(channel.getId()) == false)
        {
            System.out.println("Channel with id " + channel.getId() + " does not exist");
            return false;
        }
        channel.updateUpdatedAt();

        channels.put(channel.getId(),channel);
        return true;
    }

    @Override
    public boolean deleteChannel(Channel channel) {

        if(channel == null)
        {
            System.out.println("Channel is empty");
            return false;
        }

        if(channels.containsKey(channel.getId()) == false)
        {
            System.out.println("Channel with id " + channel.getId() + " does not exist");
            return false;
        }

        channels.remove(channel.getId());
        return true;
    }
}
