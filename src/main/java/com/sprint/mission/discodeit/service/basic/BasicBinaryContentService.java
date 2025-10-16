package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.dto.binarycontent.CreateAttachmentImage;
import com.sprint.mission.discodeit.dto.binarycontent.CreateProfileImage;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BasicBinaryContentService implements BinaryContentService {

  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentStorage storage;

  @Override
  public BinaryContent createBinaryContent(BinaryContentDto request) {
    BinaryContent binaryContent = new BinaryContent(
        request.fileName(),
        request.size(),
        request.contentType()
    );
    storage.put(binaryContent.getId(), request.bytes());
    return binaryContentRepository.save(binaryContent);
  }

  @Override
  @Transactional(readOnly = true)
  public BinaryContent find(UUID binaryContentId) {
    return binaryContentRepository.findById(binaryContentId)
        .orElseThrow(
            () -> new NotFoundException("BinaryContent with id " + binaryContentId + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<BinaryContent> findAllByIdIn(List<UUID> binaryContentIdList) {
    List<BinaryContent> binaryContentList = new ArrayList<>();
    for (UUID binaryContentId : binaryContentIdList) {
      binaryContentList.add(binaryContentRepository.findAll().stream()
          .filter(binaryContent -> binaryContent.getId().equals(binaryContentId)).findAny()
          .orElse(null));
    }
    return binaryContentList;
  }

  @Override
  public void delete(UUID binaryContentId) {
    if (!binaryContentRepository.existsById(binaryContentId)) {
      throw new NotFoundException("BinaryContent with id " + binaryContentId + " not found");
    }
    binaryContentRepository.deleteById(binaryContentId);
  }
}
