package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateRequestDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final UserStatusMapper userStatusMapper;

    @Transactional
    public UserStatusResponseDto create(UserStatusCreateRequestDto dto){

        if (userStatusRepository.existsById(dto.userId())) {
            throw new IllegalStateException("해당 유저에 대해 UserStatus가 이미 존재합니다.");
        }

        User user = userRepository.findById(dto.userId()).orElseThrow(
                () -> new NotFoundException("존재하지 않는 유저입니다."));

        UserStatus userStatus = userStatusMapper.toEntity(dto);
        userStatus.setLastActiveAt(Instant.now());

        userStatusRepository.save(userStatus);
        log.info("UserStatus 생성 완료: " +  userStatus.getId());

        return userStatusMapper.toDto(userStatus);
    }

    @Transactional(readOnly = true)
    public UserStatusResponseDto findById(UUID id){
        UserStatus userStatus = userStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 UserStatus입니다."));
        return userStatusMapper.toDto(userStatus);
    }

    @Transactional(readOnly = true)
    public List<UserStatusResponseDto> findAll(){
        List<UserStatus> userStatuses = userStatusRepository.findAll();
        return userStatuses.stream()
                .map(userStatusMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserStatusResponseDto update(UUID id, UserStatusUpdateRequestDto dto){
        UserStatus userStatus = userStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        userStatus.setLastActiveAt(Instant.now());
        log.info("UserStatus 번경 완료: " + userStatus.getId());
        userStatusRepository.save(userStatus); // 트랜잭션 종료 시 자동으로 update되지만 가독성을 위해 명시함.

        return userStatusMapper.toDto(userStatus);
    }

    @Transactional
    public UserStatusResponseDto updateByUserId(UUID userId, UserStatusUpdateRequestDto dto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저에 대해 UserStatus가 존재하지 않습니다."));

        userStatus.setLastActiveAt(dto.newLastActiveAt());
        log.info("UserStatus 번경 완료: " + userStatus.getId());
        userStatusRepository.save(userStatus); // 트랜잭션 종료 시 자동으로 update되지만 가독성을 위해 명시함.

        return userStatusMapper.toDto(userStatus);
    }

    @Transactional
    public void deleteById(UUID id){
        userStatusRepository.deleteById(id);
        log.info("UserStatus 삭제 완료: " + id);
    }

//    @Transactional
//    public void clear(){
//        userStatusRepository.clear();
//    }
}
