package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
@Repository
public class JCFChannelRepository implements ChannelRepository {
    private final List<Channel> channelsDate;

    public JCFChannelRepository() {

        channelsDate = new ArrayList<>();
    }

    @Override
    public Channel save(Channel channel) {
        int idx = channelsDate.indexOf(channel);
        if (idx >=0) {
            channelsDate.set(idx, channel);
        }else {
            channelsDate.add(channel);
        }
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {

        return channelsDate.stream()
                .filter(channel -> channel.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Channel> findAll() {
        return List.copyOf(channelsDate);
    }

    @Override
    public boolean existsById(UUID id) {
        return channelsDate.stream()
                .anyMatch(channel -> channel.getId().equals(id));
    }

    @Override
    public void deleteById(UUID id) {
        channelsDate.removeIf(channel -> channel.getId().equals(id));
    }
}
