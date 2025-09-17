package com.sprint.mission.repository.file;

import com.sprint.mission.dto.binarycontent.BinaryContentCreateDto;
import com.sprint.mission.entity.BinaryContent;
import com.sprint.mission.entity.User;
import com.sprint.mission.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileBinaryContentRepository extends SaveAndLoadCommon<User> implements BinaryContentRepository {

    public FileBinaryContentRepository() {
        super(User.class);
    }

    @Override
    public BinaryContent save(BinaryContentCreateDto binaryContentCreateDto) {
        return null;
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<BinaryContent> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteByUserId(UUID userId) {

    }
}
