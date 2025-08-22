package com.sprint.mission.discodeit.service.abstractservice;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;
import java.util.stream.Collectors;

public class AbstractChannelService implements ChannelService {

    protected final ChannelRepository channelRepository;

    public  AbstractChannelService(ChannelRepository channelRepository)
    {
        this.channelRepository = channelRepository;
    }

    @Override
    public boolean createChannel(String channelName) {
        if(channelName.isEmpty())
        {
            System.out.println("Channel name is empty");
            return false;
        }

        Channel chan = new Channel(channelName);
        return channelRepository.save(chan);
    }

    @Override
    public List<Channel> getChannels() {

        Map<UUID, Channel> channels = channelRepository.getAllChannels();
        if(channels == null)
        {
            System.out.println("Channels is empty");
            return null;
        }

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

        Map<UUID, Channel> channels = channelRepository.getAllChannels();
        if(channels == null)
        {
            System.out.println("Channels is empty");
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

        return channelRepository.update(channel);
    }

    @Override
    public boolean deleteChannel(Channel channel) {

        if(channel == null)
        {
            System.out.println("Channel is empty");
            return false;
        }

        return channelRepository.delete(channel.getId());
    }
}
