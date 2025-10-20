package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;

import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentStorage binaryContentStorage;

    @Override
    @Transactional
    public BinaryContent create(BinaryContentDto binaryContentDto) {
        UUID contentId = binaryContentDto.id();
        BinaryContent binaryContent = new BinaryContent(
                binaryContentDto.filename(),
                binaryContentDto.size(),
                binaryContentDto.contentType()
        );
        BinaryContent savedContent = binaryContentRepository.save(binaryContent);
        return savedContent;
    }

    @Override
    public BinaryContent find(UUID binaryContentId) {
        return binaryContentRepository.findById(binaryContentId)
                .orElseThrow(
                        () -> new EntityNotFoundException("BinaryContent with id " + binaryContentId + " not found"));
    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> binaryContentIds) {
        return binaryContentRepository.findAllById(binaryContentIds);
    }

    @Override
    @Transactional
    public void delete(UUID binaryContentId) {
        BinaryContent binaryContent = binaryContentRepository.findById(binaryContentId)
                .orElseThrow(() -> new EntityNotFoundException("BinaryContent with id " + binaryContentId + " not found"));

        binaryContentRepository.delete(binaryContent);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> download(UUID binaryContentId) {
        BinaryContent binaryContent = binaryContentRepository.findById(binaryContentId)
                .orElseThrow(()-> new EntityNotFoundException("BinaryContent with id " + binaryContentId + " not found"));
    BinaryContentDto dto = new BinaryContentDto(
            binaryContent.getId(),
            binaryContent.getContentType(),
            binaryContent.getFileName(),
            binaryContent.getSize(),
            null
    );
    return binaryContentStorage.download(dto);
    }
}