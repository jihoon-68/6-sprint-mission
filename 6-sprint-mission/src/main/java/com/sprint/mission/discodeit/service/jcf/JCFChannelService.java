package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final List<Channel> channelData;

    public JCFChannelService() {
        this.channelData = new ArrayList<>();
    }

    @Override
    public Channel read(String name) {
        return channelData.stream().filter(ch -> ch.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public Channel create(String name) {
        Channel channel = new Channel(name);
        channelData.add(channel);
        return channel;
    }

    @Override
    public List<Channel> allRead() {
        return channelData;
    }

    @Override
    public Channel modify(UUID id) {
        return channelData.stream().filter(ch -> ch.getCommon().getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public Channel delete(UUID id) {
        return channelData.stream().filter(ch -> ch.getCommon().getId().equals(id)).findAny().orElse(null);
    }

}
