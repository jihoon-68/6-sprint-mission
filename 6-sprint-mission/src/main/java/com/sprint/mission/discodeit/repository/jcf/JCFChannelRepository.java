package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> channelMap;

    public JCFChannelRepository() {
        this.channelMap = new HashMap<>();
    }

    @Override
    public Channel save(Channel channel) {
        this.channelMap.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return Optional.ofNullable(this.channelMap.get(id));
    }

    @Override
    public List<Channel> findAll() {
        return this.channelMap.values().stream().toList();          // 넘겨진 후 불변, 수정하면 예외 발생
    }

    @Override
    public boolean existsById(UUID id) {
        return this.channelMap.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.channelMap.remove(id);
    }
}
