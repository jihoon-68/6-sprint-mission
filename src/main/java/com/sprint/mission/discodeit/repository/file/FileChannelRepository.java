package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.File;
import java.util.*;

public class FileChannelRepository extends AbstractFileRepository<Channel> implements ChannelRepository {
    private Map<UUID, Channel> data;

    public FileChannelRepository(String baseDir) {
        super(new File(baseDir, "channels.ser"));
        this.data = load();
    }

    @Override public Channel save(Channel channel) { data.put(channel.getId(), channel); save(data); return channel; }
    @Override public Optional<Channel> findById(UUID id) { return Optional.ofNullable(data.get(id)); }
    @Override public List<Channel> findAll() { return new ArrayList<>(data.values()); }
    @Override public boolean deleteById(UUID id) { boolean r = data.remove(id) != null; if (r) save(data); return r; }
}