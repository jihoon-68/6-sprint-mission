package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JCFBinaryContentRepository implements BinaryContentRepository {
    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        return null;
    }

    @Override
    public Optional<BinaryContent> findByUserId(UUID userId) {
        return Optional.empty();
    }

    @Override
    public Map<UUID, BinaryContent> findAll() {
        return Map.of();
    }

    @Override
    public void deleteByUserId(UUID id) {

    }
}
