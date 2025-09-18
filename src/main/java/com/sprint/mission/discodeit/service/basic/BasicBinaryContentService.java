package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.BinaryContent.CreateBinaryContentDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContent create(CreateBinaryContentDTO createBinaryContentDTO) {
        BinaryContent binaryContent = new BinaryContent(createBinaryContentDTO);
        binaryContentRepository.save(binaryContent);
        return binaryContentRepository.save(binaryContent);
    }

    @Override
    public BinaryContent findById(UUID id) {
        return binaryContentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("BinaryContent with id: " + id + " not found"));
    }

    @Override
    public List<BinaryContent> findAll() {
        return List.copyOf(binaryContentRepository.findAll());
    }

    @Override
    public void deleteById(UUID id) {
        binaryContentRepository.deleteById(id);
    }
}
