package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateRequestDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    public UserStatusResponseDto create(UserStatusCreateRequestDto dto){
        if (userRepository.findById(dto.userId()) != null) {
            if (userStatusRepository.findById(dto.userId()) != null){
                throw new IllegalStateException("해당 유저에 대해 UserStatus가 이미 존재합니다.");
            }
            UserStatus userStatus = new UserStatus(dto.userId());
            userStatusRepository.save(userStatus);
            return new UserStatusResponseDto(
                    userStatus.getId(),
                    userStatus.getUserId(),
                    userStatus.getLastlyConnectedAt()
            );
        }
        else {
            throw new NoSuchElementException("존재하지 않는 유저입니다.");
        }
    }

    public UserStatusResponseDto findById(UUID id){
        UserStatus userStatus = userStatusRepository.findById(id);
        return new UserStatusResponseDto(
                userStatus.getId(),
                userStatus.getUserId(),
                userStatus.getLastlyConnectedAt()
        );
    }

    public List<UserStatusResponseDto> findAll(){
        List<UserStatus> userStatuses = userStatusRepository.findAll();
        return userStatuses.stream()
                .map(us -> new UserStatusResponseDto(us.getId(), us.getUserId(), us.getLastlyConnectedAt()))
                .toList();
    }

    public UserStatusResponseDto update(UserStatusUpdateRequestDto dto){
        UserStatus userStatus = userStatusRepository.findById(dto.id());
        userStatus.setLastlyConnectedAt(dto.lastlyConnectedAt());
        userStatusRepository.save(userStatus);
        return new UserStatusResponseDto(
                userStatus.getId(),
                userStatus.getUserId(),
                userStatus.getLastlyConnectedAt()
        );
    }

    public UserStatusResponseDto updateByUserId(UUID userId, Instant lastlyConnectedAt) {
        User user = userRepository.findById(userId);
        UserStatus userStatus
                = userStatusRepository.findByUserId(userId);
        if (userStatus == null) {
            throw new NoSuchElementException("해당 유저의 상태 정보가 존재하지 않습니다.");
        }
        userStatus.setLastlyConnectedAt(lastlyConnectedAt);
        userStatusRepository.save(userStatus);
        return new UserStatusResponseDto(
                userStatus.getId(),
                userStatus.getUserId(),
                userStatus.getLastlyConnectedAt()
        );
    }

    public void deleteById(UUID id){
        UserStatus userStatus = userStatusRepository.findById(id);
        if (userStatus == null) {
            throw new NoSuchElementException("존재하지 않는 UserStatus입니다.");
        }
        userStatusRepository.delete(userStatus);
        System.out.println("UserStatus 삭제 완료: " + id);
    }

    public void clear(){
        userStatusRepository.clear();
    }
}
