package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.BinaryContent.CreateBinaryContentDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContent  create(CreateBinaryContentDTO createBinaryContentDTO);
    BinaryContent findById(UUID id);
    List<BinaryContent> findAll();
    void deleteById(UUID id);
}
