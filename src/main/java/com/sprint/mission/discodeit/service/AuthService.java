package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.Auth.LoginDTO;
import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.DTO.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.Enum.UserStatusType;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    public FindUserDTO login(LoginDTO loginDTO) throws AuthenticationException {
        User user = userRepository.findByEmail(loginDTO.userName())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if(!user.getPassword().equals(loginDTO.password())) {
            throw new AuthenticationException("Wrong password");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        //로그인시 오프라인 -> 온라인으로
        userStatus.update(new UpdateUserStatusDTO(userStatus.getId(),user.getId(),userStatus.getLastAccessAt(), UserStatusType.ONLINE));
        userStatusRepository.save(userStatus);
        return new FindUserDTO(user,userStatus);
    }
}
