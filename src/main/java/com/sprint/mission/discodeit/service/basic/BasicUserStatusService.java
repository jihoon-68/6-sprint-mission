package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.userStatus.model.UserStatusDto;
import com.sprint.mission.discodeit.dto.userStatus.request.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.userStatus.response.UserStatusFindAllResponse;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository  userRepository;

    @Override
    public void createUserStatus(UserStatusCreateRequest request) {
        if (userRepository.findById(request.getUserId()) == null) {
            log.warn("유저를 찾을 수 없습니다. - userId = {}", request.getUserId());
            throw new IllegalArgumentException("유저를 찾을 수 없습니다.");
        }
        UserStatus userStatus = userStatusRepository.findByUserId(request.getUserId());
        if (userStatus != null) {
            log.warn("이미 존재하는 UserStatus - userId = {}, userStatusId: {}", request.getUserId(),  userStatus.getUserId());
            throw new IllegalStateException("이미 존재하는 UserStatus입니다.");
        }

        userStatus = new UserStatus(request.getUserId());
        userStatusRepository.save(userStatus);
    }

    @Override
    public UserStatusDto findOneById(UUID id) {
        UserStatus userStatus = userStatusRepository.findByUserId(id);

        if (userStatus == null) {
            log.warn("UserStatus를 찾을 수 없습니다. - userId = {}", id);
            throw new IllegalArgumentException("UserStatus를 찾을 수 없습니다.");
        }

        return UserStatus.toDto(userStatus);
    }

    @Override
    public UserStatusFindAllResponse findAll() {
        List<UserStatus>  userStatuses = userStatusRepository.findAll();

        UserStatusFindAllResponse response = new UserStatusFindAllResponse();
        response.setUserStatusDtos(userStatuses.stream().map(UserStatus::toDto).toList());

        return response;
    }
}
