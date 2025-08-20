package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data = new HashMap<>();

    @Override
    public void createChannel(String ChName) {
        Channel channel = new Channel(ChName);
        data.put(channel.getId(), channel);
    }

    @Override
    public Channel getChannel(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> getChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateChannel(UUID id, String newChName) {
        Channel channel = data.get(id);
        if (channel != null) {
            channel.update(newChName);
        }
    }

    @Override
    public void deleteChannel(UUID id) {
        data.remove(id);
    }
}
