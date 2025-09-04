package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.LoginDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

    private final UserRepository userRepository;

    @Override
    public User login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.userName())
                .orElseThrow(() -> new NoSuchElementException("user not found. name : " + loginDto.userName()));

        if(user.getPassword().equals(loginDto.password())){
            return user;
        }
        else
            throw new NoSuchElementException("invalid password. name : " + loginDto.userName());
    }
}
