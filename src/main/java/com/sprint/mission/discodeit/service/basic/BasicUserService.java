package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserRequest;
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
    public User create(UserRequest userRequest) {

        User userEmail = userRepository.findByEmail(userRequest.email()).orElse(null);
        User userName = userRepository.findByEmail(userRequest.username()).orElse(null);

        if(userEmail != null && userName != null){
            throw new IllegalStateException("User already exists. Check if email or username exists. email : " + userRequest.email() + " and username : " + userRequest.username());
        }

        User user = new User(userRequest.username(), userRequest.email(), userRequest.password());
        UserStatus userStatus = new UserStatus(user.getId());
        userStatusRepository.save(userStatus);

        if(userRequest.attatchmentUrl() != null && !userRequest.attatchmentUrl().trim().isEmpty() == false) {
            BinaryContent binaryContent = new BinaryContent(user.getId(),null, userRequest.attatchmentUrl());
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
    public User update(UserRequest  userRequest) {
        User user = userRepository.findById(userRequest.userId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + userRequest.userId() + " not found"));
        user.update(userRequest.username(), userRequest.email(), userRequest.password());

        BinaryContent content = binaryContentRepository.findByUserId(userRequest.userId())
                .orElseThrow(() -> new NoSuchElementException("BinaryContent with id " + userRequest.userId() + " not found"));

        content.changeProfileImage(userRequest.attatchmentUrl());
        binaryContentRepository.save(content);
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
        binaryContentRepository.deleteByUserId(userId);
        userStatusRepository.deleteByUserId(userId);
    }
}
