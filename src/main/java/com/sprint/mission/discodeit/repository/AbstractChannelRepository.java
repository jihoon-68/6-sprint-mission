package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Channel.ChannelType;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.repository.RepositoryMessageConstants.CHANNEL_ID_ALREADY_EXISTS;

public abstract class AbstractChannelRepository implements ChannelRepository {

    protected AbstractChannelRepository() {
    }

    @Override
    public Channel save(Channel channel) {
        Validator<Channel> validator = Validator.identity();
        if (channel.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    Channel::getId,
                    () -> new IllegalStateException(CHANNEL_ID_ALREADY_EXISTS.formatted(channel.getId()))
            ));
        }
        Map<UUID, Channel> data = getData();
        Channel validated = validator.validate(data, channel);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public Optional<Channel> find(UUID id) {
        Map<UUID, Channel> data = getData();
        Channel channel = data.get(id);
        return Optional.ofNullable(channel);
    }

    @Override
    public Optional<Channel> findPublic(UUID id) {
        return this.find(id).filter(channel -> channel.getChannelType() == ChannelType.PUBLIC);
    }

    @Override
    public Optional<Channel> findPrivate(UUID id) {
        return this.find(id).filter(channel -> channel.getChannelType() == ChannelType.PRIVATE);
    }

    @Override
    public Set<Channel> findAll() {
        Map<UUID, Channel> data = getData();
        return Set.copyOf(data.values());
    }

    @Override
    public Set<Channel> findAllPublic() {
        return findAll().stream()
                .filter(channel -> channel.getChannelType() == ChannelType.PUBLIC)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Channel> findAllPrivate() {
        return findAll().stream()
                .filter(channel -> channel.getChannelType() == ChannelType.PRIVATE)
                .collect(Collectors.toSet());
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, Channel> data = getData();
        data.remove(id);
        flush(data);
    }

    protected abstract Map<UUID, Channel> getData();

    protected void flush(Map<?, ?> data) {
    }
}
