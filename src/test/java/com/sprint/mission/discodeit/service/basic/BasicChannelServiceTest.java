package com.sprint.mission.discodeit.service.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.UpdatePrivateChannelException;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("BasicChannelService")
@ExtendWith(MockitoExtension.class)
class BasicChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;
    @Mock
    private ReadStatusRepository readStatusRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChannelMapper channelMapper;
    @InjectMocks
    private BasicChannelService channelService;

    @Test
    @DisplayName("Public channel create 성공")
    void create_public_success() {
        PublicChannelCreateRequest request =
            new PublicChannelCreateRequest("general", "team discussion");
        ChannelDto expected = new ChannelDto(UUID.randomUUID(), ChannelType.PUBLIC, "general",
            "team discussion", List.of(), Instant.now());

        given(channelRepository.save(any(Channel.class)))
            .willAnswer(invocation -> invocation.getArgument(0));
        given(channelMapper.toDto(any(Channel.class))).willReturn(expected);

        ChannelDto result = channelService.create(request);

        assertEquals(expected, result);
        then(channelRepository).should().save(any(Channel.class));
        then(channelMapper).should().toDto(any(Channel.class));
    }

    @Test
    @DisplayName("Public channel create 실패 - 저장 중 예외 발생 시 전파")
    void create_public_failure_onSave() {
        PublicChannelCreateRequest request =
            new PublicChannelCreateRequest("general", "team discussion");

        given(channelRepository.save(any(Channel.class)))
            .willThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> channelService.create(request));
        then(channelRepository).should().save(any(Channel.class));
        then(channelMapper).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Private channel create 성공")
    void create_private_success() {
        UUID participantId = UUID.randomUUID();
        PrivateChannelCreateRequest request =
            new PrivateChannelCreateRequest(List.of(participantId));
        User participant = new User("alice", "alice@example.com", "pass", null);
        ChannelDto expected =
            new ChannelDto(UUID.randomUUID(), ChannelType.PRIVATE, null, null, List.of(),
                Instant.now());

        given(channelRepository.save(any(Channel.class)))
            .willAnswer(invocation -> invocation.getArgument(0));
        given(userRepository.findAllById(request.participantIds()))
            .willReturn(List.of(participant));
        given(readStatusRepository.saveAll(any()))
            .willAnswer(invocation -> invocation.getArgument(0));
        given(channelMapper.toDto(any(Channel.class))).willReturn(expected);

        ChannelDto result = channelService.create(request);

        assertEquals(expected, result);
        then(channelRepository).should().save(any(Channel.class));
        then(userRepository).should().findAllById(request.participantIds());
        then(readStatusRepository).should().saveAll(any());
        then(channelMapper).should().toDto(any(Channel.class));
    }

    @Test
    @DisplayName("Private channel create 실패 - 참여자 조회 중 예외 발생")
    void create_private_failure_onParticipantLookup() {
        PrivateChannelCreateRequest request =
            new PrivateChannelCreateRequest(List.of(UUID.randomUUID()));

        given(channelRepository.save(any(Channel.class)))
            .willAnswer(invocation -> invocation.getArgument(0));
        given(userRepository.findAllById(request.participantIds()))
            .willThrow(new RuntimeException("user repo failure"));

        assertThrows(RuntimeException.class, () -> channelService.create(request));
        then(userRepository).should().findAllById(request.participantIds());
        then(readStatusRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("update 성공 - Public channel 정보 갱신")
    void update_success() {
        UUID channelId = UUID.randomUUID();
        Channel channel = new Channel(ChannelType.PUBLIC, "general", "desc");
        ChannelDto expected = new ChannelDto(channelId, ChannelType.PUBLIC, "random", "new desc",
            List.of(), Instant.now());

        ReflectionTestUtils.setField(channel, "id", channelId);
        PublicChannelUpdateRequest request =
            new PublicChannelUpdateRequest("random", "new desc");

        given(channelRepository.findById(channelId)).willReturn(Optional.of(channel));
        given(channelMapper.toDto(channel)).willReturn(expected);

        ChannelDto result = channelService.update(channelId, request);

        assertEquals("random", channel.getName());
        assertEquals("new desc", channel.getDescription());
        assertEquals(expected, result);
        then(channelMapper).should().toDto(channel);
    }

    @Test
    @DisplayName("update 실패 - Private channel 수정 시도 시 예외")
    void update_failure_privateChannel() {
        UUID channelId = UUID.randomUUID();
        Channel channel = new Channel(ChannelType.PRIVATE, null, null);
        PublicChannelUpdateRequest request =
            new PublicChannelUpdateRequest("random", "new desc");

        given(channelRepository.findById(channelId)).willReturn(Optional.of(channel));

        assertThrows(UpdatePrivateChannelException.class,
            () -> channelService.update(channelId, request));
        then(channelRepository).should().findById(channelId);
        then(channelMapper).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("delete 성공 - 연관 데이터 제거 후 채널 삭제")
    void delete_success() {
        UUID channelId = UUID.randomUUID();

        given(channelRepository.existsById(channelId)).willReturn(true);

        channelService.delete(channelId);

        then(channelRepository).should().existsById(channelId);
        then(messageRepository).should().deleteAllByChannelId(channelId);
        then(readStatusRepository).should().deleteAllByChannelId(channelId);
        then(channelRepository).should().deleteById(channelId);
    }

    @Test
    @DisplayName("delete 실패 - 채널 미존재 시 ChannelNotFoundException")
    void delete_failure_channelNotFound() {
        UUID channelId = UUID.randomUUID();

        given(channelRepository.existsById(channelId)).willReturn(false);

        assertThrows(ChannelNotFoundException.class, () -> channelService.delete(channelId));
        then(channelRepository).should().existsById(channelId);
        then(messageRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("findAllByUserId 성공 - 구독 채널과 공개 채널 조회")
    void findAllByUserId_success() {
        UUID userId = UUID.randomUUID();
        Channel privateChannel = new Channel(ChannelType.PRIVATE, null, null);
        UUID privateChannelId = UUID.randomUUID();
        ReflectionTestUtils.setField(privateChannel, "id", privateChannelId);
        ReadStatus readStatus = new ReadStatus(new User("alice", "alice@example.com", "pass", null),
            privateChannel, Instant.now());
        Channel publicChannel = new Channel(ChannelType.PUBLIC, "general", "desc");
        ChannelDto privateDto = new ChannelDto(privateChannelId, ChannelType.PRIVATE, null, null,
            List.of(), Instant.now());
        ChannelDto publicDto = new ChannelDto(UUID.randomUUID(), ChannelType.PUBLIC, "general",
            "desc",
            List.of(), Instant.now());

        given(readStatusRepository.findAllByUserId(userId)).willReturn(List.of(readStatus));
        given(channelRepository.findAllByTypeOrIdIn(eq(ChannelType.PUBLIC), anyList()))
            .willReturn(List.of(privateChannel, publicChannel));
        given(channelMapper.toDto(privateChannel)).willReturn(privateDto);
        given(channelMapper.toDto(publicChannel)).willReturn(publicDto);

        List<ChannelDto> result = channelService.findAllByUserId(userId);

        assertEquals(List.of(privateDto, publicDto), result);
        then(readStatusRepository).should().findAllByUserId(userId);
        then(channelRepository).should()
            .findAllByTypeOrIdIn(eq(ChannelType.PUBLIC), anyList());
        then(channelMapper).should().toDto(privateChannel);
        then(channelMapper).should().toDto(publicChannel);
    }

    @Test
    @DisplayName("findAllByUserId 실패 - 저장소 예외 전파")
    void findAllByUserId_failure_repositoryError() {
        UUID userId = UUID.randomUUID();

        given(readStatusRepository.findAllByUserId(userId))
            .willThrow(new RuntimeException("lookup failed"));

        assertThrows(RuntimeException.class, () -> channelService.findAllByUserId(userId));
        then(channelRepository).shouldHaveNoInteractions();
    }
}
