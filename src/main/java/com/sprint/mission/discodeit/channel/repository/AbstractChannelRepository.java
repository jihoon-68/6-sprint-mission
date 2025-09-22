package com.sprint.mission.discodeit.channel.repository;

import com.sprint.mission.discodeit.channel.domain.Channel;
import com.sprint.mission.discodeit.channel.domain.Channel.ChannelType;
import com.sprint.mission.discodeit.common.exception.DiscodeitException.DiscodeitPersistenceException;
import com.sprint.mission.discodeit.common.persistence.Validator;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractChannelRepository implements ChannelRepository {

    protected AbstractChannelRepository() {
    }

    @Override
    public Channel save(Channel channel) {
        Validator<Channel> validator = Validator.identity();
        if (channel.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    Channel::getId,
                    channelKey -> new DiscodeitPersistenceException(
                            "Channel ID already exists: '%s'".formatted(channelKey.getId()))
            ));
        }
        Map<UUID, Channel> data = getData();
        Channel validated = validator.validate(data, channel);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public Channel findById(UUID id) {
        Map<UUID, Channel> data = getData();
        Channel channel = data.get(id);
        if (channel == null) {
            throw new DiscodeitPersistenceException("Channel not found for ID: '%s'".formatted(id));
        }
        return channel;
    }

    @Override
    public Channel findPublicById(UUID id) {
        Map<UUID, Channel> data = getData();
        Channel channel = data.get(id);
        if (channel == null) {
            throw new DiscodeitPersistenceException("Channel not found for ID: '%s'".formatted(id));
        }
        if (channel.getChannelType() != ChannelType.PUBLIC) {
            throw new DiscodeitPersistenceException("Channel is not public: '%s'".formatted(id));
        }
        return channel;
    }

    @Override
    public Channel findPrivateById(UUID id) {
        Map<UUID, Channel> data = getData();
        Channel channel = data.get(id);
        if (channel == null) {
            throw new DiscodeitPersistenceException("Channel not found for ID: '%s'".formatted(id));
        }
        if (channel.getChannelType() != ChannelType.PRIVATE) {
            throw new DiscodeitPersistenceException("Channel is not private: '%s'".formatted(id));
        }
        return channel;
    }

    @Override
    public Iterable<Channel> findAll() {
        Map<UUID, Channel> data = getData();
        return Set.copyOf(data.values());
    }

    @Override
    public Iterable<Channel> findAllPublic() {
        Map<UUID, Channel> data = getData();
        return data.values()
                .stream()
                .filter(channel -> channel.getChannelType() == ChannelType.PUBLIC)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Iterable<Channel> findAllPrivate() {
        Map<UUID, Channel> data = getData();
        return data.values()
                .stream()
                .filter(channel -> channel.getChannelType() == ChannelType.PRIVATE)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void deleteById(UUID id) {
        Map<UUID, Channel> data = getData();
        data.remove(id);
        flush(data);
    }

    protected abstract Map<UUID, Channel> getData();

    protected void flush(Map<?, ?> data) {
    }
}
