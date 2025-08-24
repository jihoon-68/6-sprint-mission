package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Channel.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {

    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        data = new HashMap<>();
    }

    @Override
    public Channel create(ChannelType channelType, String channelName, String description) {
        Channel newChannel = Channel.of(channelType, channelName, description);
        data.put(newChannel.id(), newChannel);
        return newChannel;
    }

    @Override
    public Optional<Channel> read(UUID id) {
        Channel channel = data.get(id);
        return Optional.ofNullable(channel);
    }

    @Override
    public Set<Channel> readAll() {
        return new HashSet<>(data.values());
    }

    @Override
    public Channel setPublicChannel(UUID id) {
        return data.compute(id,
                (keyId, valueChannel) -> Objects.requireNonNull(valueChannel).setPublicChannel());
    }

    @Override
    public Channel updateChannelName(UUID id, String newChannelName) {
        return data.compute(id,
                (keyId, valueChannel) -> Objects.requireNonNull(valueChannel).updateChannelName(newChannelName));
    }

    @Override
    public Channel updateDescription(UUID id, String newDescription) {
        return data.compute(id,
                (keyId, valueChannel) -> Objects.requireNonNull(valueChannel).updateDescription(newDescription));
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
