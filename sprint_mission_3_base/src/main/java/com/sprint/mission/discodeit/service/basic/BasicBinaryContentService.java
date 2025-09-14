package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.BinaryContent.CreateBinaryContentRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContent create(CreateBinaryContentRequest createBinaryContentRequest) {
        BinaryContent binaryContent = new BinaryContent(
                createBinaryContentRequest.profileId(),
                createBinaryContentRequest.messageId(),
                createBinaryContentRequest.content()
        );

        binaryContentRepository.save(binaryContent);
        return binaryContent;
    }

    @Override
    public BinaryContent find(UUID id) {
        return binaryContentRepository.find(id).orElse(null);
    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        List<BinaryContent> contents = new ArrayList<>();
        for(UUID id : ids) {
            BinaryContent content = binaryContentRepository.find(id).orElse(null);
            if(content != null) {
                contents.add(content);
            }
        }
        return contents;
    }

    @Override
    public void delete(UUID id) {
        binaryContentRepository.delete(id);
    }
}
