package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {

    private final Map<UUID, Channel> data = new HashMap<>();

    @Override
    public Channel createChannel(String name, String description) {
        Channel channel = new Channel(name, description);
        return data.put(channel.getId(), channel);
    }

    @Override
    public Channel getChannelById(UUID id) {
        if (data.containsKey(id)) {
            return data.get(id);
        } else {
            System.out.println("해당 ID에 맞는 채널이 없습니다.");
            return null;
        }
    }

    @Override
    public List<Channel> getAllChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Channel updateChannel(UUID id, String newName, String newDescription) {
        Channel channel = data.get(id);
        channel.updateChannel(newName, newDescription);
        return channel;
    }

    @Override
    public boolean deleteChannel(UUID id) {
        return data.remove(id) != null;
    }
}
