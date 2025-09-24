package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.BinaryContent.FileDTO;
import com.sprint.mission.discodeit.DTO.User.UserCreateRequest;
import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.DTO.User.UpdateUserDTO;
import com.sprint.mission.discodeit.DTO.User.UpdateUserResponse;
import com.sprint.mission.discodeit.utilit.FileUpload;
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

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final FileUpload fileUpload;

    @Override
    public User create(List<MultipartFile> multipartFile, UserCreateRequest userCreateRequest) {
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

        //유저 중복 확인후 유저 생성
        User user = userRepository.save(new User(
                        userCreateRequest.username(),
                        userCreateRequest.email(),
                        userCreateRequest.password()
                )
        );

        //파일 있으면 파일 생성
        if (!multipartFile.isEmpty()) {
            FileDTO profile = fileUpload.upload(multipartFile).get(0);

            BinaryContent binaryContent = binaryContentRepository.save(
                    new BinaryContent(
                            profile.FileName(),
                            profile.file().getFreeSpace(),
                            profile.file().getClass().getTypeName(),
                            profile.file().toString().getBytes(StandardCharsets.UTF_8)
                    )
            );

            user.update(UpdateUserDTO.getFileInput(binaryContent.getId()));
        }
        userStatusRepository.save(new UserStatus(user.getId()));
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
                .collect(Collectors.toMap(UserStatus::getUserId, Function.identity()));

        return users.stream()
                .map(user -> new FindUserDTO(user, userStatusMap.get(user.getId())))
                .toList();
    }

    @Override
    public UpdateUserResponse update(List<MultipartFile> multipartFile, UpdateUserDTO updateUserDTO) {

        //유저 중복 확인
        if (isDuplicate(updateUserDTO.username(), updateUserDTO.email())) {
            throw new DuplicateFormatFlagsException("Username already exists");
        }

        User user = userRepository.findById(updateUserDTO.id()).orElseThrow(() -> new NoSuchElementException("user not found"));

        if (!multipartFile.isEmpty()) {
            FileDTO profile = fileUpload.upload(multipartFile).get(0);
            UUID profileId = binaryContentRepository.save(
                    new BinaryContent(
                            profile.FileName(),
                            profile.file().getFreeSpace(),
                            profile.file().getClass().getTypeName(),
                            profile.file().toString().getBytes(StandardCharsets.UTF_8)
                    )).getId();

            user.update(new UpdateUserDTO(
                    updateUserDTO.id(),
                    updateUserDTO.username(),
                    updateUserDTO.email(),
                    profileId,
                    updateUserDTO.password())
            );
            User userE = userRepository.save(user);
            return new UpdateUserResponse(userE);
        }

        user.update(updateUserDTO);
        return new UpdateUserResponse(user);
    }

    @Override
    public void delete(UUID id) {
        UserStatus userStatus = userStatusRepository.findByUserId(id).orElseThrow(() -> new NoSuchElementException("UserStatus not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

        binaryContentRepository.deleteById(user.getProfileId());
        userStatusRepository.deleteById(userStatus.getId());
        userRepository.deleteById(id);
    }

    public boolean isDuplicate(String name, String email) {
        List<User> users = userRepository.findAll();
        return users.stream().anyMatch(user -> user.getUsername().equals(name)) ||
                users.stream().anyMatch(user -> user.getEmail().equals(email));
    }
}
