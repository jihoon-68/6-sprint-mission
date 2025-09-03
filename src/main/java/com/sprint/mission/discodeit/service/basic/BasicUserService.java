package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserCreateRequest;
import com.sprint.mission.discodeit.dto.UserDto;
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
    private final BinaryContentRepository binaryContentRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public User create(UserCreateRequest userCreateRequest) {

        User userEmail = userRepository.findByEmail(userCreateRequest.email()).orElse(null);
        User userName = userRepository.findByEmail(userCreateRequest.username()).orElse(null);

        if(userEmail != null && userName != null){
            throw new IllegalStateException("User already exists. Check if email or username exists. email : " + userCreateRequest.email() + " and username : " + userCreateRequest.username());
        }

        User user = new User(userCreateRequest.username(), userCreateRequest.email(), userCreateRequest.password());
        UserStatus userStatus = new UserStatus(user.getId());
        userStatusRepository.save(userStatus);

        if(userCreateRequest.attatchmentUrl() != null && !userCreateRequest.attatchmentUrl().trim().isEmpty() == false) {
            BinaryContent binaryContent = new BinaryContent(user.getId(),null, userCreateRequest.attatchmentUrl());
            binaryContentRepository.save(binaryContent);
        }

        return userRepository.save(user);
    }

    @Override
    public UserDto find(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        UserStatus status = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Userstatus with id " + userId + " not found"));
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), status.isOnlineUser());
        return userDto;
    }

    @Override
    public List<UserDto> findAll() {

        List<User> users = userRepository.findAll();
        Map<UUID, UserStatus> userStatuses = userStatusRepository.findAll();
        if(users == null)
            return null;

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {

            boolean isOnline = false;
            if(userStatuses != null && userStatuses.isEmpty() == false && userStatuses.containsKey(user.getId())) {
                isOnline = userStatuses.get(user.getId()).isOnlineUser();
            }

            UserDto temp = new UserDto(user.getId(), user.getUsername(), user.getEmail(), isOnline);
            userDtos.add(temp);
        }
        
        return userDtos;
    }

    @Override
    public User update(UUID userId, String newUsername, String newEmail, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        user.update(newUsername, newEmail, newPassword);
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }
}
