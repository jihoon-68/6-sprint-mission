package com.sprint.mission.repository;

import com.sprint.mission.dto.binarycontent.BinaryContentCreateDto;
import com.sprint.mission.entity.BinaryContent;
import com.sprint.mission.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BinaryContentRepository {

    BinaryContent save(BinaryContentCreateDto binaryContentCreateDto);
    Optional<BinaryContent> findById(UUID id);
    List<BinaryContent> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
    void deleteByUserId(UUID userId);
}
