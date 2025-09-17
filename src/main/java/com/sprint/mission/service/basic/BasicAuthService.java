package com.sprint.mission.service.basic;

import com.sprint.mission.exception.NotFoundException;
import com.sprint.mission.exception.PasswordErrorException;
import com.sprint.mission.repository.UserRepository;
import com.sprint.mission.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    private final UserRepository userRepository;


    @Override
    public boolean login(String username, String password) {
        if(!userRepository.existsByUserName(username)) {
            throw new NotFoundException("Username not found");
        }
        if(userRepository.findAll().stream().noneMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password))) {
            throw new PasswordErrorException("Wrong password");
        }
        return true;
    }

}
