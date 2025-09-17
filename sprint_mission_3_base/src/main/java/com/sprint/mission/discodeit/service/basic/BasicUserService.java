package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.User.UserDto;
import com.sprint.mission.discodeit.DTO.User.UserResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;


    @Override
    public User create(UserResponse request) {
        User userEmail = userRepository.findByEmail(request.email()).orElse(null);
        User userName = userRepository.findByEmail(request.username()).orElse(null);

        if(userEmail != null && userName != null) {
            throw new IllegalArgumentException("User with email " + request.email() + " or username " + request.username() + " already exists");
        }

        User user = new User(request.username(), request.email(), request.password());
        UserStatus userStatus = new UserStatus(user.getId());
        userStatusRepository.save(userStatus);

        if(request.attachments() != null) {
            BinaryContent binaryContent = new BinaryContent(user.getId(),null, request.attachments());
            binaryContentRepository.save(binaryContent);
        }

        return userRepository.save(user);
    }

    @Override
    public UserDto find(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Userstatus with id" + userId + " not found"));
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), userStatus.isActive());
        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        Map<UUID, UserStatus> userStatuses = userStatusRepository.findAll();

        if(users == null || userStatuses == null) {
            throw new NoSuchElementException("Users or UserStatus not found");
        }

        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users) {
            boolean isActive = false;

            if(userStatuses.containsKey(user.getId())) {
                isActive = userStatuses.get(user.getId()).isActive();
            }

            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), isActive);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public User update(UserResponse request) {
        User user = userRepository.findById(request.userid())
                .orElseThrow(() -> new NoSuchElementException("User with id " + request.userid() + " not found"));
        user.update(request.username(), request.email(), request.password());

        BinaryContent content = binaryContentRepository.findByUserId(request.userid())
                .orElseThrow(() -> new NoSuchElementException("BinaryContent with id " + request.userid() + " not found"));
        content.update(request.attachments());
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
        binaryContentRepository.deleteByUserId(userId);
        userStatusRepository.deleteById(userId);
    }
}
