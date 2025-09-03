package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;

public class BasicChannelService {
    private final ChannelRepository channelRepository;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel create(String channelName, User user){
        Channel channel = new Channel(channelName,user);
        channelRepository.createChannel(channel);
        return channel;
    }


}
