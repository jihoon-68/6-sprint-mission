package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {
    private final FileStore<Message> store;

    public FileMessageRepository(String path) { this.store = new FileStore<>(path); }

    @Override
    public Message save(Message message) {
        Map<UUID, Message> map = store.loadMapOrEmpty();
        map.put(message.getId(), message);
        store.saveMap(map);
        return message;
    }

    @Override
    public Optional<Message> readId(UUID id) {
        return Optional.ofNullable(store.loadMapOrEmpty().get(id));
    }

    @Override
    public List<Message> readContent(String content) {
        return store.loadMapOrEmpty().values().stream()
                .filter(message -> message.getContent().contains(content))
                .toList();
    }

    @Override
    public List<Message> readUser(User user) {
        return store.loadMapOrEmpty().values().stream()
                .filter(message -> message.getUser().equals(user.getId()))
                .toList();
    }

    @Override
    public List<Message> readChannel(Channel channel) {
        return store.loadMapOrEmpty().values().stream()
                .filter(message -> message.getChannel().equals(channel.getId()))
                .toList();
    }

    @Override
    public List<Message> readAll() {
        return List.copyOf(store.loadMapOrEmpty().values());
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, Message> map = store.loadMapOrEmpty();
        boolean removed = map.remove(id) != null;
        if (removed) { store.saveMap(map); }
        return removed;
    }
}
