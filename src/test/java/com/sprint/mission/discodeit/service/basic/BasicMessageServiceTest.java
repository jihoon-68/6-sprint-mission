package com.sprint.mission.discodeit.service.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.sprint.mission.discodeit.dto.data.MessageDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("BasicMessageService")
@ExtendWith(MockitoExtension.class)
class BasicMessageServiceTest {

  @Mock
  private MessageRepository messageRepository;
  @Mock
  private ChannelRepository channelRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private MessageMapper messageMapper;
  @Mock
  private BinaryContentStorage binaryContentStorage;
  @Mock
  private BinaryContentRepository binaryContentRepository;
  @Mock
  private PageResponseMapper pageResponseMapper;
  @InjectMocks
  private BasicMessageService messageService;

  @Test
  @DisplayName("create 성공 - 첨부 파일과 함께 메시지 생성")
  void create_success() {
    UUID channelId = UUID.randomUUID();
    UUID authorId = UUID.randomUUID();
    MessageCreateRequest request =
        new MessageCreateRequest("hello", channelId, authorId);
    BinaryContentCreateRequest attachmentRequest =
        new BinaryContentCreateRequest("note.txt", "text/plain",
            "hello".getBytes(StandardCharsets.UTF_8));
    Channel channel = new Channel(ChannelType.PUBLIC, "general", "desc");
    User author = new User("alice", "alice@example.com", "pass", null);
    Message message = new Message("hello", channel, author, List.of());
    MessageDto expected = new MessageDto(UUID.randomUUID(), Instant.now(), Instant.now(), "hello",
        channelId, null, List.of());

    given(channelRepository.findById(channelId)).willReturn(Optional.of(channel));
    given(userRepository.findById(authorId)).willReturn(Optional.of(author));
    given(binaryContentRepository.save(any(BinaryContent.class)))
        .willAnswer(invocation -> invocation.getArgument(0));
    given(binaryContentStorage.put(any(), any())).willReturn(UUID.randomUUID());
    given(messageRepository.save(any(Message.class))).willAnswer(invocation -> {
      Message saved = invocation.getArgument(0);
      ReflectionTestUtils.setField(saved, "id", UUID.randomUUID());
      ReflectionTestUtils.setField(saved, "createdAt", Instant.now());
      return saved;
    });
    given(messageMapper.toDto(any(Message.class))).willReturn(expected);

    MessageDto result = messageService.create(request, List.of(attachmentRequest));

    assertEquals(expected, result);
    then(messageRepository).should().save(any(Message.class));
    then(binaryContentRepository).should().save(any(BinaryContent.class));
    then(binaryContentStorage).should().put(any(), any());
  }

  @Test
  @DisplayName("create 실패 - 채널 미존재 시 예외 발생")
  void create_failure_channelNotFound() {
    UUID channelId = UUID.randomUUID();
    MessageCreateRequest request =
        new MessageCreateRequest("hello", channelId, UUID.randomUUID());

    given(channelRepository.findById(channelId)).willReturn(Optional.empty());

    assertThrows(NoSuchElementException.class,
        () -> messageService.create(request, List.of()));
    then(channelRepository).should().findById(channelId);
    then(userRepository).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("update 성공 - 메시지 내용 변경")
  void update_success() {
    UUID messageId = UUID.randomUUID();
    Message message = new Message("hello", new Channel(ChannelType.PUBLIC, "general", "desc"),
        new User("alice", "alice@example.com", "pass", null), List.of());
    MessageUpdateRequest request = new MessageUpdateRequest("updated");
    MessageDto expected = new MessageDto(messageId, Instant.now(), Instant.now(), "updated",
        UUID.randomUUID(), null, List.of());

    given(messageRepository.findById(messageId)).willReturn(Optional.of(message));
    given(messageMapper.toDto(message)).willReturn(expected);

    MessageDto result = messageService.update(messageId, request);

    assertEquals("updated", message.getContent());
    assertEquals(expected, result);
    then(messageMapper).should().toDto(message);
  }

  @Test
  @DisplayName("update 실패 - 메시지 미존재 시 예외 발생")
  void update_failure_notFound() {
    UUID messageId = UUID.randomUUID();
    MessageUpdateRequest request = new MessageUpdateRequest("updated");

    given(messageRepository.findById(messageId)).willReturn(Optional.empty());

    assertThrows(NoSuchElementException.class,
        () -> messageService.update(messageId, request));
    then(messageRepository).should().findById(messageId);
  }

  @Test
  @DisplayName("delete 성공 - 메시지 존재 시 삭제")
  void delete_success() {
    UUID messageId = UUID.randomUUID();

    given(messageRepository.existsById(messageId)).willReturn(true);

    messageService.delete(messageId);

    then(messageRepository).should().existsById(messageId);
    then(messageRepository).should().deleteById(messageId);
  }

  @Test
  @DisplayName("delete 실패 - 메시지 미존재 시 예외 발생")
  void delete_failure_notFound() {
    UUID messageId = UUID.randomUUID();

    given(messageRepository.existsById(messageId)).willReturn(false);

    assertThrows(NoSuchElementException.class, () -> messageService.delete(messageId));
    then(messageRepository).should().existsById(messageId);
    then(messageRepository).shouldHaveNoMoreInteractions();
  }

  @Test
  @DisplayName("findAllByChannelId 성공 - 페이징 결과 반환")
  void findAllByChannelId_success() {
    UUID channelId = UUID.randomUUID();
    Instant cursor = Instant.now();
    Pageable pageable = PageRequest.of(0, 10);
    Message message = new Message("hello", new Channel(ChannelType.PUBLIC, "general", "desc"),
        new User("alice", "alice@example.com", "pass", null), List.of());
    MessageDto dto = new MessageDto(UUID.randomUUID(), Instant.now(), Instant.now(), "hello",
        channelId, null, List.of());
    Slice<Message> slice = new SliceImpl<>(List.of(message), pageable, false);
    PageResponse<MessageDto> expected =
        new PageResponse<>(List.of(dto), dto.createdAt(), 10, false, null);

    given(messageRepository.findAllByChannelIdWithAuthor(eq(channelId), any(), eq(pageable)))
        .willReturn(slice);
    given(messageMapper.toDto(message)).willReturn(dto);
    given(pageResponseMapper.fromSlice(any(Slice.class), any()))
        .willReturn(expected);

    PageResponse<MessageDto> result =
        messageService.findAllByChannelId(channelId, cursor, pageable);

    assertEquals(expected, result);
    then(messageRepository).should()
        .findAllByChannelIdWithAuthor(eq(channelId), any(), eq(pageable));
    then(pageResponseMapper).should().fromSlice(any(Slice.class), any());
  }

  @Test
  @DisplayName("findAllByChannelId 실패 - 조회 중 예외 발생")
  void findAllByChannelId_failure_repositoryError() {
    UUID channelId = UUID.randomUUID();
    Pageable pageable = PageRequest.of(0, 10);

    given(messageRepository.findAllByChannelIdWithAuthor(eq(channelId), any(), eq(pageable)))
        .willThrow(new RuntimeException("query failed"));

    assertThrows(RuntimeException.class,
        () -> messageService.findAllByChannelId(channelId, Instant.now(), pageable));
  }
}
