package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.CreateBinaryContentRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContentResponse create(CreateBinaryContentRequest request) {
        BinaryContent binaryContent = new BinaryContent();
        binaryContentRepository.save(binaryContent);
        return BinaryContentResponse.of(binaryContent);
    }

    @Override
    public BinaryContentResponse find(UUID binaryContentId) {
        return binaryContentRepository.findById(binaryContentId)
                .map(BinaryContentResponse::of)
                .orElseThrow(() -> new NoSuchElementException("BinaryContent not found with id: " + binaryContentId));
    }

    @Override
    public List<BinaryContentResponse> findAllByIdIn(List<UUID> binaryContentIds) {
        return binaryContentRepository.findAllByIdIn(binaryContentIds)
                .stream()
                .map(BinaryContentResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID binaryContentId) {
        if (!binaryContentRepository.existsById(binaryContentId)) {
            throw new NoSuchElementException("BinaryContent not found with id: " + binaryContentId);
        }
        binaryContentRepository.deleteById(binaryContentId);
    }
}
