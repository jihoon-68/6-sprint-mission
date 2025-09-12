package com.sprint.mission.discodeit.repository.jcf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

public class JCFChannelRepository implements ChannelRepository {

    private final Map<UUID, Channel> data = new HashMap<>();

    @Override
    public Channel createChannel(String name, String description) {
        Channel channel = new Channel(name, description);
        data.put(channel.getId(), channel);
        return channel;
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
    public Channel updateChannel(UUID id,String newName, String newDescription) {
        Channel channel = data.get(id);
        channel.updateChannel(newName, newDescription);
        return channel;
    }

    @Override
    public boolean deleteChannel(UUID id) {
        return data.remove(id) != null;
    }

}
