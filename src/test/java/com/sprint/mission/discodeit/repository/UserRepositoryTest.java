package com.sprint.mission.discodeit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.mission.discodeit.config.TestJpaConfig;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@Import(TestJpaConfig.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DisplayName("UserRepository")
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  @DisplayName("findByUsername - 존재하는 사용자 반환")
  void findByUsername_success() {
    User user = persistUser("john", "john@example.com");

    Optional<User> actual = userRepository.findByUsername(user.getUsername());

    assertThat(actual).isPresent();
    assertThat(actual.get().getEmail()).isEqualTo("john@example.com");
  }

  @Test
  @DisplayName("findByUsername - 존재하지 않는 사용자면 빈 Optional 반환")
  void findByUsername_failure() {
    Optional<User> actual = userRepository.findByUsername("unknown");

    assertThat(actual).isEmpty();
  }

  @Test
  @DisplayName("existsByEmail - 이메일이 존재하면 true")
  void existsByEmail_success() {
    User user = persistUser("jane", "jane@example.com");

    boolean exists = userRepository.existsByEmail(user.getEmail());

    assertThat(exists).isTrue();
  }

  @Test
  @DisplayName("existsByEmail - 이메일이 없으면 false")
  void existsByEmail_failure() {
    boolean exists = userRepository.existsByEmail("absent@example.com");

    assertThat(exists).isFalse();
  }

  @Test
  @DisplayName("findAllWithProfileAndStatus - 사용자와 프로필, 상태를 함께 조회")
  void findAllWithProfileAndStatus_success() {
    User user = persistUser("alice", "alice@example.com");

    List<User> users = userRepository.findAllWithProfileAndStatus();

    assertThat(users).hasSize(1);
    User loaded = users.get(0);
    assertThat(loaded.getId()).isEqualTo(user.getId());
    assertThat(loaded.getProfile()).isNotNull();
    assertThat(loaded.getStatus()).isNotNull();
  }

  @Test
  @DisplayName("findAllWithProfileAndStatus - 사용자가 없으면 빈 목록 반환")
  void findAllWithProfileAndStatus_failure() {
    List<User> users = userRepository.findAllWithProfileAndStatus();

    assertThat(users).isEmpty();
  }

  private User persistUser(String username, String email) {
    BinaryContent profile = new BinaryContent("profile.png", 10L, "image/png");
    User user = new User(username, email, "password", profile);
    new UserStatus(user, Instant.now());
    entityManager.persist(user);
    entityManager.flush();
    return user;
  }
}
