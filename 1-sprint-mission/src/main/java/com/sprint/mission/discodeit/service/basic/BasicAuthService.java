package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTOs.Auth.AuthInfo;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BasicAuthService implements AuthService {

    private final UserRepository userRepository;

    public BasicAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(AuthInfo info) {

        User user = userRepository.findByUsername(info.username())
                .orElseThrow(() -> new NoSuchElementException("user not found. name : " + info.username()));

        if (user.getPassword().equals(info.password())) {
            return user;
        } else
            throw new NoSuchElementException("invalid password. name : " + info.username());
    }
}
