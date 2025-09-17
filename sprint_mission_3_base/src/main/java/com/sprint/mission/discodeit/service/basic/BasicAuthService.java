package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.Login.LoginRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

    private final UserRepository userRepository;

    @Override
    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByUserName(loginRequest.userName())
                .orElseThrow(() -> new NoSuchElementException("user not found. name : " + loginRequest.userName()));

        if(user.getPassword().equals(loginRequest.password())){
            return user;
        }
        else
            throw new NoSuchElementException("invalid password. name : " + loginRequest.userName());
    }
}
