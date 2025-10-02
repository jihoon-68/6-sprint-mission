package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.CreateAttachmentImage;
import com.sprint.mission.discodeit.dto.binarycontent.CreateProfileImage;
import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.List;
import java.util.UUID;

public interface BinaryContentService {

  BinaryContent createAttachmentImage(CreateAttachmentImage createAttachmentImage);

  BinaryContent createProfileImage(CreateProfileImage createProfileImage);

  BinaryContent find(UUID binaryContentId);

  List<BinaryContent> findAllByIdIn(List<UUID> binaryContentIdList);

  void delete(UUID binaryContentId);
}
