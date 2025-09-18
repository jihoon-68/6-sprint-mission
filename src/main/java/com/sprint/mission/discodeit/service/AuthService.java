package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.security.sasl.AuthenticationException;

@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public User login(String email, String password) throws AuthenticationException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NullPointerException("User not found"));

        if(!user.getPassword().equals(password)) {
            throw new AuthenticationException("Wrong password");
        }

        return user;
    }
}
