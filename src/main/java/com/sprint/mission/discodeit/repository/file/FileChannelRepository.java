package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {
    private final FileStore<Channel> store;

    public FileChannelRepository(String path) { this.store = new FileStore<>(path); }

    @Override
    public Channel save(Channel channel) {
        Map<UUID, Channel> map = store.loadMapOrEmpty();
        map.put(channel.getId(), channel);
        store.saveMap(map);
        return channel;
    }

    @Override
    public Optional<Channel> readId(UUID id) {
        return Optional.ofNullable(store.loadMapOrEmpty().get(id));
    }

    @Override
    public List<Channel> readTitle(String title) {
        return store.loadMapOrEmpty().values().stream()
                .filter(channel -> channel.getTitle().contains(title))
                .toList();
    }

    @Override
    public List<Channel> readAll() {
        return List.copyOf(store.loadMapOrEmpty().values());
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, Channel> map = store.loadMapOrEmpty();
        boolean removed = map.remove(id) != null;
        if (removed) { store.saveMap(map); }
        return removed;
    }
}
