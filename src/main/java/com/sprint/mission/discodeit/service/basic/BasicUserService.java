package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentSave;
import com.sprint.mission.discodeit.dto.User.*;
import com.sprint.mission.discodeit.entity.BaseEntity;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentStorage binaryContentStorage;

    @Override
    public UserDto create(MultipartFile multipartFile, UserCreateRequest userCreateRequest) {
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
        BinaryContentSave profileBinaryContent = getBinaryContent(multipartFile);
        BinaryContent profile = null;
        if (profileBinaryContent != null) {
            profile = profileBinaryContent.binaryContent();
            binaryContentStorage.put(profile.getId(), profileBinaryContent.data());
        }

        //유저 생성
        User user = new User(
                userCreateRequest.username(),
                userCreateRequest.email(),
                userCreateRequest.password(),
                profile
        );

        //유저에 유저 상태 추가
        UserStatus userStatus = new UserStatus(user);
        user.update(UpdateUserDto.getStatus(userStatus));

        if (profile != null) {binaryContentRepository.save(profile);}
        userRepository.save(user);
        userStatusRepository.save(userStatus);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserStatus> userStatuses = userStatusRepository.findAll();
        Map<UUID, UserStatus> userStatusMap = userStatuses.stream()
                .collect(Collectors.toMap(BaseEntity::getId, Function.identity()));

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto update(MultipartFile multipartFile, UUID userId, UserUpdateRequest userUpdateRequest) {

        //유저 중복 확인
        if (isDuplicate(userUpdateRequest.newUsername(), userUpdateRequest.newEmail())) {
            throw new DuplicateFormatFlagsException("Username already exists");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));

        //파일 있으면 파일 생성
        BinaryContentSave profileBinaryContent = getBinaryContent(multipartFile);
        BinaryContent profile = null;
        if (profileBinaryContent != null) {
            profile = profileBinaryContent.binaryContent();
            binaryContentStorage.put(profile.getId(), profileBinaryContent.data());
        }

        user.update(UpdateUserDto.getUpdateUser(
                user.getId(),
                userUpdateRequest,
                profile
        ));

        if (profile != null) {binaryContentRepository.save(profile);}
        userRepository.save(user);
        return userMapper.toDto(user);
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

    public BinaryContentSave getBinaryContent(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        } else {
            try {
                BinaryContent binaryContent = new BinaryContent(
                        multipartFile.getOriginalFilename(),
                        multipartFile.getSize(),
                        multipartFile.getContentType()
                );

                return new BinaryContentSave(binaryContent, multipartFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
