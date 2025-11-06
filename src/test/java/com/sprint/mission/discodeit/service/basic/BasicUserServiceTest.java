package com.sprint.mission.discodeit.service.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.DuplicateEmailException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("BasicUserService")
@ExtendWith(MockitoExtension.class)
class BasicUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BinaryContentRepository binaryContentRepository;
    @Mock
    private BinaryContentStorage binaryContentStorage;
    @InjectMocks
    private BasicUserService userService;

    @Test
    @DisplayName("create 성공 - 고유한 사용자 정보면 UserDto 반환")
    void create_success() {
        UserCreateRequest request = new UserCreateRequest("john", "john@example.com", "secret");
        UserDto expected = new UserDto(UUID.randomUUID(), "john", "john@example.com", null, true);

        given(userRepository.existsByEmail("john@example.com")).willReturn(false);
        given(userRepository.existsByUsername("john")).willReturn(false);
        given(userRepository.save(any(User.class))).willAnswer(
            invocation -> invocation.getArgument(0));
        given(userMapper.toDto(any(User.class))).willReturn(expected);

        UserDto result = userService.create(request, Optional.empty());

        assertEquals(expected, result);
        then(userRepository).should().save(any(User.class));
        then(userMapper).should().toDto(any(User.class));
        then(binaryContentRepository).shouldHaveNoInteractions();
        then(binaryContentStorage).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("create 실패 - 이메일 중복 시 DuplicateEmailException 발생")
    void create_failure_duplicateEmail() {
        UserCreateRequest request = new UserCreateRequest("john", "john@example.com", "secret");

        given(userRepository.existsByEmail("john@example.com")).willReturn(true);

        assertThrows(DuplicateEmailException.class,
            () -> userService.create(request, Optional.empty()));
        then(userRepository).should().existsByEmail("john@example.com");
        then(userRepository).shouldHaveNoMoreInteractions();
        then(userMapper).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("update 성공 - 사용자 정보와 프로필을 수정")
    void update_success() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User("old", "old@example.com", "oldPass", null);
        UserUpdateRequest request = new UserUpdateRequest("newName", "new@example.com", "newPass");
        byte[] bytes = "profile".getBytes(StandardCharsets.UTF_8);
        BinaryContentCreateRequest profileRequest =
            new BinaryContentCreateRequest("profile.png", "image/png", bytes);
        UserDto expected =
            new UserDto(userId, "newName", "new@example.com", null, true);

        given(userRepository.findById(userId)).willReturn(Optional.of(existingUser));
        given(userRepository.existsByEmail("new@example.com")).willReturn(false);
        given(userRepository.existsByUsername("newName")).willReturn(false);
        given(binaryContentRepository.save(any(BinaryContent.class)))
            .willAnswer(invocation -> {
                BinaryContent saved = invocation.getArgument(0);
                ReflectionTestUtils.setField(saved, "id", UUID.randomUUID());
                return saved;
            });
        given(binaryContentStorage.put(any(UUID.class), eq(bytes))).willReturn(UUID.randomUUID());
        given(userMapper.toDto(existingUser)).willReturn(expected);

        UserDto result =
            userService.update(userId, request, Optional.of(profileRequest));

        assertEquals("newName", existingUser.getUsername());
        assertEquals("new@example.com", existingUser.getEmail());
        assertEquals(expected, result);
        then(binaryContentRepository).should().save(any(BinaryContent.class));
        then(binaryContentStorage).should().put(any(UUID.class), eq(bytes));
        then(userMapper).should().toDto(existingUser);
    }

    @Test
    @DisplayName("update 실패 - 사용자 미존재 시 UserNotFoundException 발생")
    void update_failure_userNotFound() {
        UUID userId = UUID.randomUUID();
        UserUpdateRequest request = new UserUpdateRequest("newName", "new@example.com", "newPass");

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
            () -> userService.update(userId, request, Optional.empty()));
        then(userRepository).should().findById(userId);
    }

    @Test
    @DisplayName("delete 성공 - 사용자 미존재라면 예외 없이 delete 호출")
    void delete_success() {
        UUID userId = UUID.randomUUID();

        given(userRepository.existsById(userId)).willReturn(false);

        userService.delete(userId);

        then(userRepository).should().existsById(userId);
        then(userRepository).should().deleteById(userId);
    }

    @Test
    @DisplayName("delete 실패 - 사용자 존재 시 UserNotFoundException 발생")
    void delete_failure_userExists() {
        UUID userId = UUID.randomUUID();

        given(userRepository.existsById(userId)).willReturn(true);

        assertThrows(UserNotFoundException.class, () -> userService.delete(userId));
        then(userRepository).should().existsById(userId);
        then(userRepository).shouldHaveNoMoreInteractions();
    }
}
