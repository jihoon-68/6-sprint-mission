package com.sprint.mission.service.basic;

import com.sprint.mission.dto.binarycontent.BinaryContentCreateDto;
import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.dto.user.UserReturnDto;
import com.sprint.mission.dto.user.UserUpdateDto;
import com.sprint.mission.dto.userstatus.UserStatusCreateDto;
import com.sprint.mission.entity.User;
import com.sprint.mission.entity.UserStatus;
import com.sprint.mission.exception.DuplicateException;
import com.sprint.mission.exception.NotFoundException;
import com.sprint.mission.repository.BinaryContentRepository;
import com.sprint.mission.repository.UserRepository;
import com.sprint.mission.repository.UserStatusRepository;
import com.sprint.mission.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;


    @Override
    public User create(UserCreateDto userCreateDto) {
        if(userRepository.existsByUserName(userCreateDto.getUserName())) {
            throw new DuplicateException("UserName already exists");
        }
        if(userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new DuplicateException("Email already exists");
        }
        binaryContentRepository.save(new BinaryContentCreateDto(userCreateDto.getProfileImage().getId()));
        User user = userRepository.save(userCreateDto);
        userStatusRepository.save(new UserStatusCreateDto(user.getId(), false, Instant.now()));
        return user;
    }

    @Override
    public UserReturnDto find(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserReturnDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileId(), user.getStatus());
    }

    @Override
    public List<UserReturnDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserReturnDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileId(), user.getStatus()))
                .toList();
    }

    @Override
    public void update(UserUpdateDto userUpdateDto) {

    }

    @Override
    public void delete(UUID id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            userStatusRepository.deleteByUserId(id);
            binaryContentRepository.deleteByUserId(id);
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
