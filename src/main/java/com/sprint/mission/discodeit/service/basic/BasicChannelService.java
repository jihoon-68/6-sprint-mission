package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.PrivateChannelDto;
import com.sprint.mission.discodeit.dto.PublicChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.*;

@Service
public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final ChannelMapper channelMapper;
    private final ReadStatusMapper readStatusMapper;

    public BasicChannelService(
            ChannelRepository channelRepository,
            UserRepository userRepository,
            ReadStatusRepository readStatusRepository,
            MessageRepository messageRepository,
            ChannelMapper channelMapper,
            ReadStatusMapper readStatusMapper
    ) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.readStatusRepository = readStatusRepository;
        this.messageRepository = messageRepository;
        this.channelMapper = channelMapper;
        this.readStatusMapper = readStatusMapper;
    }

    @Override
    public PublicChannelDto.Response create(PublicChannelDto.Request request) {
        Channel channel = channelMapper.fromPublicChannel(request);
        channel = channelRepository.save(channel);
        return channelMapper.toPublicChannelResponse(channel);
    }

    @Override
    public PrivateChannelDto.Response create(PrivateChannelDto.Request request) {
        Channel channel = channelMapper.fromPrivateChannel(request);
        channel = channelRepository.save(channel);
        for (UUID joinedUserId : request.joinedUserIds()) {
            User joinedUser = userRepository.find(joinedUserId).orElseThrow(() ->
                    new IllegalArgumentException(USER_NOT_FOUND_BY_JOINED_USER_ID.formatted(joinedUserId)));
            ReadStatus readStatus = readStatusMapper.of(joinedUser.getId(), channel.getId(), Instant.MIN);
            readStatusRepository.save(readStatus);
        }
        return channelMapper.toPrivateChannelResponse(channel);
    }

    @Override
    public PublicChannelDto.MessageInfoResponse readPublic(UUID id) {
        Channel channel = channelRepository.findPublic(id).orElseThrow(() ->
                new IllegalArgumentException(PUBLIC_CHANNEL_NOT_FOUND_BY_ID.formatted(id)));
        Instant lastMessageAt = messageRepository.findLastCreatedAt(channel.getId()).orElse(Instant.MIN);
        return channelMapper.toResponse(channel, lastMessageAt);
    }

    @Override
    public PrivateChannelDto.Response readPrivate(UUID id) {
        Channel channel = channelRepository.findPrivate(id).orElseThrow(() ->
                new IllegalArgumentException(PRIVATE_CHANNEL_NOT_FOUND_BY_ID.formatted(id)));
        return channelMapper.toPrivateChannelResponse(channel);
    }

    @Override
    public Set<PublicChannelDto.MessageInfoResponse> readAllPublic() {
        return channelRepository.findAllPublic()
                .stream()
                .map(channel -> {
                    Instant lastMessageAt = messageRepository.findLastCreatedAt(channel.getId()).orElse(Instant.MIN);
                    return channelMapper.toResponse(channel, lastMessageAt);
                })
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<PrivateChannelDto.Response> readAllPrivate() {
        return channelRepository.findAllPrivate()
                .stream()
                .map(channelMapper::toPrivateChannelResponse)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public PublicChannelDto.Response update(UUID id, PublicChannelDto.Request request) {
        Channel channel = channelRepository.findPublic(id).orElseThrow(() ->
                new IllegalArgumentException(PUBLIC_CHANNEL_NOT_FOUND_BY_ID.formatted(id)));
        channel = channel.update(request.channelName(), request.description());
        channel = channelRepository.save(channel);
        return channelMapper.toPublicChannelResponse(channel);
    }

    @Override
    public void delete(UUID id) {
        Channel channel = channelRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(CHANNEL_NOT_FOUND_BY_ID.formatted(id)));
        channel.getJoinedUserIds().forEach(joinedUserId -> readStatusRepository.delete(joinedUserId, channel.getId()));
        messageRepository.findAll(id).forEach(message -> messageRepository.delete(message.getId()));
        channelRepository.delete(channel.getId());
    }
}
