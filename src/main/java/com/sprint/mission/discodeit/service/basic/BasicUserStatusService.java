package com.sprint.mission.discodeit.service.basic;


import com.sprint.mission.discodeit.dto.userstatusdto.UserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatus create(UserStatusDto userStatusDto) {
        if(userRepository.existsById(userStatusDto.userId())){
            throw new NoSuchElementException("유저가 없습니다: " + userStatusDto.userId());
        }
        if(userStatusRepository.existsById(userStatusDto.userStatusDtoId())){
            throw new IllegalArgumentException("이미 유저상태 객체가 있습니다");
        }
        UserStatus userStatus = new UserStatus(userStatusDto.userId());
        return userStatusRepository.save(userStatus);
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
    public UserStatus update(UserStatusDto userStatusDto) {
        UserStatus userStatus = userStatusRepository.findById(userStatusDto.userId())
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + userStatusDto.userId() + " not found"));
        userStatus.update();
        return userStatusRepository.save(userStatus);
    }

    @Override
    public UserStatus updateByUserId(UUID userId){
        UserStatus userStatus = userStatusRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        userStatus.update();
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
