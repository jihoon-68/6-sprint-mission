package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFBinaryContentRepository implements BinaryContentRepository {

    private final List<BinaryContent> data = new ArrayList<>();

    @Override
    public void save(BinaryContent binaryContent) {
        data.removeIf(b -> b.getId().equals(binaryContent.getId()));
        data.add(binaryContent);
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return data.stream()
                .filter(binaryContent -> binaryContent.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        return data.stream()
                .filter(binaryContent -> ids.contains(binaryContent.getId()))
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        boolean removed = data.removeIf(b -> b.getId().equals(id));
        if (!removed) {
            throw new NotFoundException("존재하지 않는 BinaryContent입니다. id=" + id);
        }
    }

    @Override
    public void clear() {
        data.clear();
    }
}
