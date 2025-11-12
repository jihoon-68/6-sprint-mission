package com.sprint.mission.discodeit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.mission.discodeit.config.TestJpaConfig;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@Import(TestJpaConfig.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DisplayName("MessageRepository")
class MessageRepositoryTest {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  @DisplayName("findAllByChannelIdWithAuthor - 페이징 결과 반환")
  void findAllByChannelIdWithAuthor_success() {
    Channel channel = persistChannel("general");
    User author = persistUser("author", "author@example.com", true);
    Message message = persistMessage("hello world", channel, author);
    entityManager.flush();
    entityManager.refresh(message);

    Instant cursor = message.getCreatedAt().plusSeconds(1);
    Pageable pageable = PageRequest.of(0, 20);

    Slice<Message> slice =
        messageRepository.findAllByChannelIdWithAuthor(channel.getId(), cursor, pageable);

    assertThat(slice.getContent()).hasSize(1);
    Message loaded = slice.getContent().get(0);
    assertThat(loaded.getAuthor().getUsername()).isEqualTo(author.getUsername());
    assertThat(loaded.getChannel().getId()).isEqualTo(channel.getId());
  }

  @Test
  @DisplayName("findAllByChannelIdWithAuthor - 결과가 없으면 빈 Slice")
  void findAllByChannelIdWithAuthor_failure() {
    Slice<Message> slice =
        messageRepository.findAllByChannelIdWithAuthor(UUID.randomUUID(), Instant.now(),
            PageRequest.of(0, 20));

    assertThat(slice.getContent()).isEmpty();
  }

  @Test
  @DisplayName("findLastMessageAtByChannelId - 마지막 메시지 생성 시간을 반환")
  void findLastMessageAtByChannelId_success() {
    Channel channel = persistChannel("random");
    User author = persistUser("carol", "carol@example.com", true);
    Message message = persistMessage("latest", channel, author);
    entityManager.flush();
    entityManager.refresh(message);

    Optional<Instant> result = messageRepository.findLastMessageAtByChannelId(channel.getId());

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(message.getCreatedAt());
  }

  @Test
  @DisplayName("findLastMessageAtByChannelId - 메시지가 없으면 빈 Optional")
  void findLastMessageAtByChannelId_failure() {
    Optional<Instant> result =
        messageRepository.findLastMessageAtByChannelId(UUID.randomUUID());

    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("deleteAllByChannelId - 채널 메시지 일괄 삭제")
  void deleteAllByChannelId_success() {
    Channel channel = persistChannel("team");
    User author = persistUser("david", "david@example.com", true);
    persistMessage("to be removed", channel, author);
    entityManager.flush();

    messageRepository.deleteAllByChannelId(channel.getId());
    entityManager.flush();

    Slice<Message> slice =
        messageRepository.findAllByChannelIdWithAuthor(channel.getId(), Instant.now(),
            PageRequest.of(0, 10));
    assertThat(slice.getContent()).isEmpty();
  }

  @Test
  @DisplayName("deleteAllByChannelId - 메시지가 없어도 예외 없이 수행")
  void deleteAllByChannelId_noMessages() {
    messageRepository.deleteAllByChannelId(UUID.randomUUID());
  }

  private Channel persistChannel(String name) {
    Channel channel = new Channel(ChannelType.PUBLIC, name, "description");
    entityManager.persist(channel);
    return channel;
  }

  private User persistUser(String username, String email, boolean includeProfile) {
    BinaryContent profile = includeProfile
        ? new BinaryContent("profile.png", 100L, "image/png")
        : null;
    User user = new User(username, email, "password", profile);
    new UserStatus(user, Instant.now());
    entityManager.persist(user);
    return user;
  }

  private Message persistMessage(String content, Channel channel, User author) {
    Message message = new Message(content, channel, author, new ArrayList<>());
    entityManager.persist(message);
    return message;
  }
}
