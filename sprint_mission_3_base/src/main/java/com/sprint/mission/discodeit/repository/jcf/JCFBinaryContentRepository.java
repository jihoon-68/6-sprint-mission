package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "repository.type", havingValue = "jcf")
public class JCFBinaryContentRepository implements BinaryContentRepository {
    private final Map<UUID, BinaryContent> data;

    public JCFBinaryContentRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        this.data.put(binaryContent.getId(), binaryContent);
        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> find(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public Optional<BinaryContent> findByUserId(UUID userId) {
        return this.data.values().stream().filter(content -> content.getProfileId().equals(userId)).findFirst();
    }

    @Override
    public Map<UUID, BinaryContent> findAll() {
        return this.data;
    }

    @Override
    public void deleteByUserId(UUID userId) {
        this.data.values().removeIf(content -> content.getProfileId().equals(userId));
    }

    @Override
    public void deleteByMessageId(UUID messageId) {
        this.data.values().removeIf(content -> content.getMessageId().equals(messageId));
    }

    @Override
    public void delete(UUID id) {
        this.data.remove(id);
    }
}
