package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.UserEntity;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.exception.user.NoSuchUserException;
import com.sprint.mission.discodeit.exception.user.PasswordMismatchException;
import com.sprint.mission.discodeit.mapper.UserEntityMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.utils.SecurityUtil;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

  private final UserRepository userRepository;
  private final UserEntityMapper userEntityMapper;
  private final SecurityUtil securityUtil = new SecurityUtil();

  @Override
  public UserDTO.User login(UserDTO.LoginCommand loginCommand) {

    UserEntity userEntity = userRepository.findByUsername(loginCommand.username())
        .orElseThrow(NoSuchUserException::new);

    if (userEntity.getPassword().equals(securityUtil.hashPassword(loginCommand.password()))) {
      return userEntityMapper.toUser(userEntity);
    } else {
      throw new PasswordMismatchException();
    }

  }
}
