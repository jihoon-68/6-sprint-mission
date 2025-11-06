package com.sprint.mission.discodeit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.mission.discodeit.config.TestJpaConfig;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@Import(TestJpaConfig.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DisplayName("ChannelRepository")
class ChannelRepositoryTest {

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  @DisplayName("findAllByTypeOrIdIn - PUBLIC 타입과 지정된 채널을 함께 반환")
  void findAllByTypeOrIdIn_success() {
    Channel publicChannel = persistChannel(ChannelType.PUBLIC, "general", "desc");
    Channel privateChannel = persistChannel(ChannelType.PRIVATE, null, null);
    User user = persistUser("reader", "reader@example.com");
    ReadStatus readStatus = new ReadStatus(user, privateChannel, Instant.now());
    entityManager.persist(readStatus);
    entityManager.flush();

    List<Channel> channels =
        channelRepository.findAllByTypeOrIdIn(ChannelType.PUBLIC, List.of(privateChannel.getId()));

    assertThat(channels)
        .extracting(Channel::getId)
        .containsExactlyInAnyOrder(publicChannel.getId(), privateChannel.getId());
  }

  @Test
  @DisplayName("findAllByTypeOrIdIn - 해당 타입과 ID가 없으면 빈 목록")
  void findAllByTypeOrIdIn_failure() {
    persistChannel(ChannelType.PRIVATE, null, null);

    List<Channel> channels =
        channelRepository.findAllByTypeOrIdIn(ChannelType.PUBLIC, List.of(UUID.randomUUID()));

    assertThat(channels).isEmpty();
  }

  private Channel persistChannel(ChannelType type, String name, String description) {
    Channel channel = new Channel(type, name, description);
    entityManager.persist(channel);
    return channel;
  }

  private User persistUser(String username, String email) {
    User user = new User(username, email, "password", null);
    new UserStatus(user, Instant.now());
    entityManager.persist(user);
    return user;
  }
}
