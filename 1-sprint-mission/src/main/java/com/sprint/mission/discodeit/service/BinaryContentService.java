package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTOs.BinaryContent.CreateBinaryContentDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContent create(CreateBinaryContentDTO createBinaryContentDto);
    BinaryContent find(UUID id);
    List<BinaryContent> findAllByIdIn(List<UUID> ids);
    void delete(UUID id);
}