package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.User.UpdateUserDTO;
import com.sprint.mission.discodeit.DTO.UserStatus.CreateUserStatusDTO;
import com.sprint.mission.discodeit.DTO.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatus create(CreateUserStatusDTO createUserStatusDTO) {
        User user = userRepository.findById(createUserStatusDTO.userId()).orElseThrow(
                ()-> new NullPointerException("user not found"));
        if (userStatusRepository.findByUserId(createUserStatusDTO.userId()).isPresent()) {
            throw new IllegalStateException("Duplicate UserStatus");
        }
        return new UserStatus(user.getId());
    }

    @Override
    public UserStatus findById(UUID id) {
        return userStatusRepository.findById(id).orElseThrow(()-> new NullPointerException("user not found"));
    }

    @Override
    public List<UserStatus> findAll() {
        return List.copyOf(userStatusRepository.findAll());
    }

    @Override
    public void update(UpdateUserStatusDTO  updateUserStatusDTO) {
        UserStatus userStatus = findById(updateUserStatusDTO.userId());
        userStatus.update(updateUserStatusDTO);
        userStatusRepository.save(userStatus);
    }

    @Override
    public void updateByUserId(UpdateUserDTO  updateUserDTO) {
        User user = userRepository.findById(updateUserDTO.id()).orElseThrow(()-> new NullPointerException("user not found"));
        user.update(updateUserDTO);
        userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.delete(id);
    }
}
