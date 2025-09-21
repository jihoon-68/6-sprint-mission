package com.sprint.mission.discodeit.service.basic;




import com.sprint.mission.discodeit.dto.userstatusdto.CreateUserStatus;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatus create(CreateUserStatus createUserStatus) {
        if(userRepository.existsById(createUserStatus.userId())){
            throw new NoSuchElementException("유저가 없습니다: " + createUserStatus.userId());
        }
        if(userStatusRepository.existsById(createUserStatus.userStatusDtoId())){
            throw new IllegalArgumentException("이미 유저상태 객체가 있습니다");
        }
        return userStatusRepository.save(UserStatus.fromUser(createUserStatus.userId(), Instant.now()));
    }

    @Override
    public UserStatus find(UUID userStatusId) {
        return userStatusRepository.findById(userStatusId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + userStatusId + " not found"));
    }

    @Override
    public List<UserStatus> findAll() {
        return userStatusRepository.findAll();
    }

    @Override
    public UserStatus update(UUID userStatusId, CreateUserStatus createUserStatus) {
        UserStatus userStatus = userStatusRepository.findById(userStatusId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + createUserStatus.userId() + " not found"));
        userStatus.update(Instant.now());
        return userStatusRepository.save(userStatus);
    }

    @Override
    public UserStatus updateByUserId(UUID userId){
        UserStatus userStatus = userStatusRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저 없음"));
        userStatus.update(Instant.now());
        return userStatusRepository.save(userStatus);
    }

    @Override
    public void delete(UUID userStatusId) {
        if (!userStatusRepository.existsById(userStatusId)) {
            throw new NoSuchElementException("UserStatus with id " + userStatusId + " not found");
        }
        userStatusRepository.deleteById(userStatusId);
    }
}
