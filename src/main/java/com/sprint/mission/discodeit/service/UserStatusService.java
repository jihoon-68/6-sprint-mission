package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateRequestDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    public UserStatusResponseDto create(UserStatusCreateRequestDto dto){
        if (userRepository.findById(dto.userId()).isPresent()) {
            if (userStatusRepository.findById(dto.userId()).isPresent()){
                throw new IllegalStateException("해당 유저에 대해 UserStatus가 이미 존재합니다.");
            }
            UserStatus userStatus = new UserStatus(dto.userId());
            userStatusRepository.save(userStatus);
            return new UserStatusResponseDto(
                    userStatus.getId(),
                    userStatus.getUserId()
                    // userStatus.getLastlyConnectedAt()
            );
        }
        else {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
    }

    public UserStatusResponseDto findById(UUID id){
        UserStatus userStatus = userStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 UserStatus입니다."));
        return new UserStatusResponseDto(
                userStatus.getId(),
                userStatus.getUserId()
                // userStatus.getLastlyConnectedAt()
        );
    }

    public List<UserStatusResponseDto> findAll(){
        List<UserStatus> userStatuses = userStatusRepository.findAll();
        return userStatuses.stream()
                .map(us -> new UserStatusResponseDto(us.getId(), us.getUserId())) // us.getLastlyConnectedAt()
                .toList();
    }

    public UserStatusResponseDto update(UUID id, UserStatusUpdateRequestDto dto){
        UserStatus userStatus = userStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        userStatus.setLastlyConnectedAt(Instant.now());
        userStatusRepository.save(userStatus);
        return new UserStatusResponseDto(
                userStatus.getId(),
                userStatus.getUserId()
                // userStatus.getLastlyConnectedAt()
        );
    }

    public UserStatusResponseDto updateByUserId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 UserStatus입니다."));

        userStatus.setLastlyConnectedAt(Instant.now());
        userStatusRepository.save(userStatus);
        return new UserStatusResponseDto(
                userStatus.getId(),
                userStatus.getUserId()
                // userStatus.getLastlyConnectedAt()
        );
    }

    public void deleteById(UUID id){
        userStatusRepository.deleteById(id); // 존재하지 않는 경우의 예외처리는 리포지토리에 구현됨.
        log.info("UserStatus 삭제 완료: " + id);
    }

    public void clear(){
        userStatusRepository.clear();
    }
}
