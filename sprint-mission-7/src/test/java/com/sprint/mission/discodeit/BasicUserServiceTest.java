package com.sprint.mission.discodeit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BasicUserServiceTest {

  @Mock
  UserRepository userRepository;
  @Mock
  UserStatusRepository userStatusRepository;
  @Mock
  UserMapper userMapper;
  @Mock
  BinaryContentRepository binaryContentRepository;
  @Mock
  BinaryContentStorage binaryContentStorage;

  @InjectMocks
  BasicUserService userService;

  private UserCreateRequest createReq;
  private User userEntity;
  private UserDto userDto;

  @BeforeEach
  void setUp() {
    createReq = new UserCreateRequest("alice", "alice@test.com", "pw1234");
    userEntity = new User("alice", "alice@test.com", "pw1234", null);
    userDto = new UserDto(UUID.randomUUID(), "alice", "alice@test.com", null, null);
  }

  @Test
  @DisplayName("성공: 프로필 없이 생성")
  void create_success_without_profile() {
    // given
    given(userRepository.existsByEmail("alice@test.com")).willReturn(false);
    given(userRepository.existsByUsername("alice")).willReturn(false);
    given(userMapper.toDto(any(User.class))).willReturn(userDto);

    // when
    UserDto result = userService.create(createReq, Optional.empty());

    // then
    assertThat(result).isNotNull();
    assertThat(result.email()).isEqualTo("alice@test.com");
    then(userRepository).should().save(any(User.class));
    then(binaryContentRepository).should(never()).save(any());
  }
}
