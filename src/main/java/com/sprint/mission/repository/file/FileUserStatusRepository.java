package com.sprint.mission.repository.file;


import com.sprint.mission.dto.userstatus.UserStatusCreateDto;
import com.sprint.mission.entity.UserStatus;
import com.sprint.mission.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileUserStatusRepository extends SaveAndLoadCommon<UserStatus> implements UserStatusRepository {

    public FileUserStatusRepository() {
        super(UserStatus.class);
    }

    @Override
    public UserStatus save(UserStatusCreateDto userStatusCreateDto) {
        UserStatus userStatus = new UserStatus(userStatusCreateDto.getUserId(), userStatusCreateDto.isOnline(), userStatusCreateDto.getLastActiveAt());
        save(userStatus);
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        if(load(id).isEmpty()) return Optional.empty();
        return load(id);
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        List<UserStatus> userStatuses = loadAll();
        return userStatuses.stream().filter(userStatus -> userStatus.getUserId().equals(userId)).findFirst();
    }

    @Override
    public List<UserStatus> findAll() {
        return loadAll();
    }

    @Override
    public boolean existsById(UUID id) {
        List<UserStatus> userStatuses = loadAll();
        return userStatuses.stream().anyMatch(userStatus -> userStatus.getId().equals(id));
    }

    @Override
    public void deleteById(UUID id) {
        delete(id);
    }

    @Override
    public void deleteByUserId(UUID userId) {
        Optional<UserStatus> userStatus = findByUserId(userId);
        delete(userStatus.get().getId());
    }
}
