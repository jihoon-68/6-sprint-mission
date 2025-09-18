package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.CreateAttachmentImageDto;
import com.sprint.mission.discodeit.dto.binarycontent.CreateProfileImageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    public final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContent createAttachmentImage(CreateAttachmentImageDto createAttachmentImageDto){
        BinaryContent binaryContent = new BinaryContent(createAttachmentImageDto.bytes());
        return binaryContentRepository.save(binaryContent);
    }
    @Override
    public BinaryContent createProfileImage(CreateProfileImageDto createProfileImageDto){
        BinaryContent binaryContent = new BinaryContent(createProfileImageDto.bytes());
        return binaryContentRepository.save(binaryContent);
    }
    @Override
    public BinaryContent find(UUID binaryContentId){
        return binaryContentRepository.findById(binaryContentId)
                .orElseThrow(() -> new NoSuchElementException("BinaryContent with id " + binaryContentId + " not found"));
    }
    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> binaryContentIdList){
        List<BinaryContent> binaryContentList = new ArrayList<>();
        for(UUID binaryContentId : binaryContentIdList){
            binaryContentList.add(binaryContentRepository.findAll().stream().filter(binaryContent -> binaryContent.getId().equals(binaryContentId)).findAny().orElse(null));
        }
        return binaryContentList;
    }
    @Override
    public void delete(UUID binaryContentId){
        if (!binaryContentRepository.existsById(binaryContentId)) {
            throw new NoSuchElementException("BinaryContent with id " + binaryContentId + " not found");
        }
        binaryContentRepository.deleteById(binaryContentId);
    }
}
