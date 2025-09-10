package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

import java.util.*;

public class JCFBinaryContentRepository implements BinaryContentRepository {

    private final Map<UUID, BinaryContent> binaryContentMap;

    public JCFBinaryContentRepository() {
        this.binaryContentMap = new HashMap<>();
    }

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        this.binaryContentMap.put(binaryContent.getId(), binaryContent);
        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return Optional.ofNullable(this.binaryContentMap.get(id));
    }

    @Override
    public List<BinaryContent> findAll() {
        return this.binaryContentMap.values().stream().toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return this.binaryContentMap.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.binaryContentMap.remove(id);
    }
}
