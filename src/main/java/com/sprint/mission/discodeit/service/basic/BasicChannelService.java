package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.Channel.*;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.enumtype.ChannelType;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public ChannelDto createPublic(PublicChannelCreateRequest request) {
        Channel channel = channelRepository.save(new Channel(request.name(), request.description()));
        return channelMapper.toDto(channel, new ArrayList<>(), null);
    }

    @Override
    public ChannelDto createPrivate(PrivateChannelCreateRequest request) {
        Channel channel = new Channel();

        List<User> users = request.participantIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " not found")))
                .toList();

        List<ReadStatus> readStatuses = users.stream()
                .map(user -> new ReadStatus(channel, user))
                .toList();

        readStatusRepository.saveAll(readStatuses);

        channelRepository.save(channel);

        return channelMapper.toDto(channel, UserMapper.INSTANCE.toDtoList(users), null);
    }

    @Override
    public List<ChannelDto> findAllByUserId(UUID userId) {
        //공개 채널부터 조회
        List<Channel> channels = channelRepository.findByType(ChannelType.PUBLIC);

        //유저로 읽음 싱테 조회
        //읽음 상태로 타켓 유저가 있는 비공개 채널 전채 채널 리스트에 추가
        readStatusRepository.findByUserId(userId).stream()
                .filter(rs -> rs.getChannel().getType().equals(ChannelType.PRIVATE))
                .forEach(rs -> channels.add(rs.getChannel()));

        //디비에 검색을 위해 채널 아이디만 추출
        List<UUID> channelIds = channels.stream()
                .map(Channel::getId)
                .toList();

        //타겟 유저가 포함된 비공개 체널에 있는 유저를 체널로 읽음 상태에서 조회
        Map<UUID, List<UserDto>> privateChannelDtoUser = readStatusRepository.findByChannelIdIn(channelIds).stream()
                .collect(Collectors.groupingBy(
                        readStatus -> readStatus.getChannel().getId(),
                        Collectors.mapping(readStatus -> userMapper.toDto(readStatus.getUser()), Collectors.toList())
                ));

        //타겟 유저가 포함된 모든 체널에 최근 메시지 시간 조회
        Map<UUID, Instant> lastMessageAt = messageRepository.findByChannelIdInOrderByCreatedAtDesc(channelIds).stream()
                .collect(Collectors.toMap(message -> message.getChannel().getId(), Message::getCreatedAt));


        //엔티티 디티오로 변환
        List<ChannelDto> findChannelDTOS = channelMapper.toDtoList(channels, privateChannelDtoUser, lastMessageAt);

        return List.copyOf(findChannelDTOS);
    }

    @Override
    public ChannelDto update(UUID channelID, PublicChannelUpdateRequest request) {
        Channel channel = channelRepository.findById(channelID)
                .orElseThrow(() -> new NoSuchElementException("Channel not found"));

        if (channel.getType().equals(ChannelType.PRIVATE)) {
            throw new UnsupportedOperationException("Private channel not supported");
        }

        Message lastMessage = messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channel.getId())
                .orElse(null);

        Instant lastMessageAt = lastMessage != null ? lastMessage.getCreatedAt() : null;

        channel.update(request.newName(), request.newDescription());
        channelRepository.save(channel);
        return channelMapper.toDto(channel, new ArrayList<>(), lastMessageAt);
    }

    @Override
    public void delete(UUID channelId) {
        readStatusRepository.findAll().stream()
                .filter(rs -> rs.getChannel().getId().equals(channelId))
                .forEach(rsDelete -> readStatusRepository.deleteById(rsDelete.getId()));

        messageRepository.findAll().stream()
                .filter(m -> m.getChannel().getId().equals(channelId))
                .forEach(mDelete -> readStatusRepository.deleteById(mDelete.getId()));

        channelRepository.deleteById(channelId);
    }
}
