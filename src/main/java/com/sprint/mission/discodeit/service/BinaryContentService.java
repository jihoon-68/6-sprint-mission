package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.CreateAttachmentImageDto;
import com.sprint.mission.discodeit.dto.binarycontent.CreateProfileImageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContent createAttachmentImage(CreateAttachmentImageDto createAttachmentImageDto);
    BinaryContent createProfileImage(CreateProfileImageDto createProfileImageDto);
    BinaryContent find(UUID binaryContentId);
    List<BinaryContent> findAllByIdIn(List<UUID> binaryContentIdList);
    void delete(UUID binaryContentId);
}
