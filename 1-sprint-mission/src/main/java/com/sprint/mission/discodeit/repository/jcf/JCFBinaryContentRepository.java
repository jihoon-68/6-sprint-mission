package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@ConditionalOnProperty(
        prefix = "discodeit.repository",
        name = "type",
        havingValue = "jcf"
)
public class JCFBinaryContentRepository implements BinaryContentRepository {

    private final Map<UUID, BinaryContent> map = new HashMap<>();

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        map.put(binaryContent.getId(), binaryContent);
        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> find(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<BinaryContent> findByUserId(UUID userId) {
        return map.entrySet()
                .stream()
                .filter(x -> x.getValue().getProfileId().equals(userId))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public Map<UUID, BinaryContent> findAll() {
        return Map.of();
    }

    @Override
    public void deleteByUserId(UUID userId) {
        map.entrySet().removeIf(x -> x.getValue().getProfileId().equals(userId));
    }

    @Override
    public void deleteByMessageId(UUID messageId) {
        map.entrySet().removeIf(x -> x.getValue().getMessageId().equals(messageId));
    }

    @Override
    public void delete(UUID id) {
        map.remove(id);
    }
}