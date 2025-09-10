package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTOs.BinaryContent.CreateBinaryContentDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    public BasicBinaryContentService(BinaryContentRepository binaryContentRepository) {
        this.binaryContentRepository = binaryContentRepository;
    }

    @Override
    public BinaryContent create(CreateBinaryContentDTO createBinaryContentDto) {
        BinaryContent content = new BinaryContent(createBinaryContentDto.profileId()
                ,createBinaryContentDto.messageId()
                ,createBinaryContentDto.attatchmentUrl());

        binaryContentRepository.save(content);
        return content;
    }

    @Override
    public BinaryContent find(UUID id) {
        return binaryContentRepository.find(id).orElse(null);
    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        List<BinaryContent> contents = new ArrayList<>();
        for (UUID id : ids) {
            BinaryContent binaryContent = binaryContentRepository.find(id).orElse(null);
            if (binaryContent == null)
                continue;

            contents.add(binaryContent);
        }

        return contents;
    }

    @Override
    public void delete(UUID id) {
        binaryContentRepository.delete(id);
    }
}
