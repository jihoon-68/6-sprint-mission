package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.Channel.*;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.enumtype.ChannelType;
import com.sprint.mission.discodeit.entity.Channel;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public ChannelDto createPublic(PublicChannelCreateRequest request) {
        Channel channel = channelRepository.save(new Channel(request.name(), request.description()));
        return channelMapper.toDto(channel);
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

        return channelMapper.toDto(channel);
    }

    @Override
    public List<ChannelDto> findAllByUserId(UUID userId) {

        //공개 채널부터 다넣음
        List<Channel> channels = channelRepository.findAll().stream()
                .filter(c -> c.getType().equals(ChannelType.PUBLIC))
                .collect(Collectors.toList());

        //유저 읽음 싱테 들고옴
        List<ReadStatus> readStatusesUser = readStatusRepository.findAll().stream()
                .filter(rs -> rs.getUser().getId().equals(userId))
                .toList();

        //리드 상태로 비공개 채널 전채 채널 리스트에 추가
        for (ReadStatus readStatuses : readStatusesUser) {
            Channel channel = channelRepository.findById(readStatuses.getChannel().getId())
                    .orElseThrow(() -> new NoSuchElementException("channel not found"));
            if (channel.getType().equals(ChannelType.PUBLIC)) {
                continue;
            }
            channels.add(channel);
        }

        //에티티 디티오로 변환
        List<ChannelDto> findChannelDTOS = new ArrayList<>();
        for (Channel channel : channels) {
            findChannelDTOS.add(channelMapper.toDto(channel));
        }

        return List.copyOf(findChannelDTOS);
    }

    @Override
    public ChannelDto update(UUID channelID, PublicChannelUpdateRequest request) {
        Channel channel = channelRepository.findById(channelID)
                .orElseThrow(() -> new NoSuchElementException("Channel not found"));

        if (channel.getType().equals(ChannelType.PRIVATE)) {
            throw new UnsupportedOperationException("Private channel not supported");
        }

        channel.update(request.newName(), request.newDescription());
        channelRepository.save(channel);
        return channelMapper.toDto(channel);
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
