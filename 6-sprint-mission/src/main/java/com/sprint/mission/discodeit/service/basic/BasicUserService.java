package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.userdto.CreateUserDto;
import com.sprint.mission.discodeit.dto.userdto.FindUserDto;
import com.sprint.mission.discodeit.dto.userdto.UpdateUserDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public User create(CreateUserDto createUserDto) {
        User user;
        User findUserByName  = userRepository.findAll().stream().filter(users -> users.getUsername().equals(createUserDto.username())).findAny().orElse(null);
        User findUserByEmail = userRepository.findAll().stream().filter(users->users.getEmail().equals(createUserDto.email())).findAny().orElse(null);
        if (findUserByName != null || findUserByEmail != null) {
            throw new IllegalArgumentException("유저네임 혹은 이메일이 같은 유저가 존재합니다.");
        }
        if(createUserDto.imagePath()!=null){
            user = new User(createUserDto.username(), createUserDto.email(), createUserDto.password());
            BinaryContent binaryContent = new BinaryContent(user.getId(), createUserDto.imagePath());
            user.setBinaryContent(binaryContent);
            binaryContentRepository.save(binaryContent);
        }else{
            user = new User(createUserDto.username(), createUserDto.email(), createUserDto.password());
        }
        userStatusRepository.save(new UserStatus(user.getId()));
        return userRepository.save(user);
    }

    @Override
    public User find(FindUserDto findUserDto) {
        return userRepository.findById(findUserDto.userId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + findUserDto.userId() + " not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(UpdateUserDto updateUserDto) {
        User user = userRepository.findById(updateUserDto.userId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + updateUserDto.userId() + " not found"));
        user.update(updateUserDto.newUsername(), updateUserDto.newEmail(), updateUserDto.newPassword(), updateUserDto.newImagePath());
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
//        binaryContentRepository.deleteById(userId);
//        userStatusRepository.deleteById(userId);
    }
}
