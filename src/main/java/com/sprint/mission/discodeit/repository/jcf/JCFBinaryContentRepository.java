package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
@Repository
public class JCFBinaryContentRepository implements BinaryContentRepository {
    private final List<BinaryContent> binaryContentsDate;

    public JCFBinaryContentRepository() {

        this.binaryContentsDate = new ArrayList<>();
    }

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        int idx = binaryContentsDate.indexOf(binaryContent);
        if (idx >=0) {
            binaryContentsDate.set(idx, binaryContent);
        }else {
            binaryContentsDate.add(binaryContent);
        }
        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return binaryContentsDate.stream()
                .filter(bc -> bc.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<BinaryContent> findAll() {
        return List.copyOf(binaryContentsDate);
    }

    @Override
    public boolean existsById(UUID id) {
        return binaryContentsDate.stream()
                .anyMatch(bc -> bc.getId().equals(id));
    }

    @Override
    public void deleteById(UUID id) {
        binaryContentsDate.removeIf(bc -> bc.getId().equals(id));
    }
}
