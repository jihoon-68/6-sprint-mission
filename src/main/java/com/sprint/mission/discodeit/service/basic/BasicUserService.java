package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.User.*;
import com.sprint.mission.discodeit.entity.BaseEntity;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public User create(MultipartFile multipartFile, UserCreateRequest userCreateRequest) {
        System.out.println(userCreateRequest);

        //널 체크
        if (userCreateRequest.username() == null ||
                userCreateRequest.password() == null ||
                userCreateRequest.email() == null
        ) {
            throw new NullPointerException("value null");
        }

        //유저 중복 확인
        if (isDuplicate(userCreateRequest.username(), userCreateRequest.email())) {
            throw new DuplicateFormatFlagsException("Username already exists");
        }

        //파일 있으면 파일 생성
        BinaryContent profile = getBinaryContent(multipartFile);
        binaryContentRepository.save(profile);

        //유저 생성
        User user = userRepository.save(new User(
                        userCreateRequest.username(),
                        userCreateRequest.email(),
                        userCreateRequest.password(),
                        profile
                )
        );
        //유저에 유저 상태 추가
        user.update(UpdateUserDTO.getStatus(userStatusRepository.save(new UserStatus(user))));
        return userRepository.save(user);
    }

    @Override
    public FindUserDTO find(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found"));
        return new FindUserDTO(user, userStatus);
    }

    @Override
    public FindUserDTO findEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found"));
        return new FindUserDTO(user, userStatus);
    }

    @Override
    public List<FindUserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserStatus> userStatuses = userStatusRepository.findAll();
        Map<UUID, UserStatus> userStatusMap = userStatuses.stream()
                .collect(Collectors.toMap(BaseEntity::getId, Function.identity()));

        return users.stream()
                .map(user -> new FindUserDTO(user, userStatusMap.get(user.getId())))
                .toList();
    }

    @Override
    public UpdateUserResponse update(MultipartFile multipartFile, UUID userId, UserUpdateRequest userUpdateRequest) {

        //유저 중복 확인
        if (isDuplicate(userUpdateRequest.newUsername(), userUpdateRequest.newEmail())) {
            throw new DuplicateFormatFlagsException("Username already exists");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));

        //파일 있으면 파일 생성
        BinaryContent profile = getBinaryContent(multipartFile);
        binaryContentRepository.save(profile);

        user.update(UpdateUserDTO.getUpdateUser(
                user.getId(),
                userUpdateRequest,
                profile
        ));

        return new UpdateUserResponse(user);
    }

    @Override
    public void delete(UUID id) {
        UserStatus userStatus = userStatusRepository.findByUserId(id).orElseThrow(() -> new NoSuchElementException("UserStatus not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

        binaryContentRepository.deleteById(user.getProfile().getId());
        userStatusRepository.deleteById(userStatus.getId());
        userRepository.deleteById(id);
    }

    public boolean isDuplicate(String name, String email) {
        List<User> users = userRepository.findAll();
        return users.stream().anyMatch(user -> user.getUsername().equals(name)) ||
                users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public BinaryContent getBinaryContent(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        } else {
            try {
                return new BinaryContent(
                        multipartFile.getOriginalFilename(),
                        multipartFile.getSize(),
                        multipartFile.getContentType(),
                        multipartFile.getBytes()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
