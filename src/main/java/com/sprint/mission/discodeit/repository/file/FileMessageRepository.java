package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.File;
import java.util.*;

public class FileMessageRepository extends AbstractFileRepository<Message> implements MessageRepository {
    private Map<UUID, Message> data;

    public FileMessageRepository(String baseDir) {
        super(new File(baseDir, "messages.ser"));
        this.data = load();
    }

    @Override public Message save(Message message) { data.put(message.getId(), message); save(data); return message; }
    @Override public Optional<Message> findById(UUID id) { return Optional.ofNullable(data.get(id)); }
    @Override public List<Message> findAll() { return new ArrayList<>(data.values()); }
    @Override public boolean deleteById(UUID id) { boolean r = data.remove(id) != null; if (r) save(data); return r; }
}
