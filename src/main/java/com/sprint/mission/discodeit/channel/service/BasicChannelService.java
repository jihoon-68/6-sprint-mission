package com.sprint.mission.discodeit.channel.service;

import com.sprint.mission.discodeit.channel.ChannelDto.Request;
import com.sprint.mission.discodeit.channel.ChannelDto.Request.PrivateRequest;
import com.sprint.mission.discodeit.channel.ChannelDto.Request.PublicRequest;
import com.sprint.mission.discodeit.channel.ChannelDto.Response;
import com.sprint.mission.discodeit.channel.ChannelDto.Response.PublicResponse;
import com.sprint.mission.discodeit.channel.ChannelMapper;
import com.sprint.mission.discodeit.channel.domain.Channel;
import com.sprint.mission.discodeit.channel.domain.Channel.ChannelType;
import com.sprint.mission.discodeit.channel.repository.ChannelRepository;
import com.sprint.mission.discodeit.message.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;

    public BasicChannelService(
            ChannelRepository channelRepository,
            MessageRepository messageRepository
    ) {
        this.channelRepository = channelRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Response createChannel(Request request) {
        return switch (request) {
            case PublicRequest(String channelName, String description) -> {
                Channel channel = ChannelMapper.ofPublic(channelName, description);
                channelRepository.save(channel);
                yield ChannelMapper.toPublicResponse(channel);
            }
            case PrivateRequest(Set<UUID> joinedUserIds) -> {
                Channel channel = ChannelMapper.ofPrivate(joinedUserIds);
                channelRepository.save(channel);
                yield ChannelMapper.toPrivateResponse(channel);
            }
        };
    }

    @Override
    public Response getChannelByChannelTypeAndId(String type, UUID id) {
        return switch (Enum.valueOf(ChannelType.class, type)) {
            case PUBLIC -> {
                Channel channel = channelRepository.findPublicById(id);
                Instant lastMessageAt = messageRepository.maxCreatedAtByChannelId(channel.getId());
                yield ChannelMapper.toPublicResponseWithLastMessageAt(channel, lastMessageAt);
            }
            case PRIVATE -> {
                Channel channel = channelRepository.findPrivateById(id);
                yield ChannelMapper.toPrivateResponse(channel);
            }
        };
    }

    @Override
    public Set<Response> getChannelsByChannelType(String type) {
        return switch (Enum.valueOf(ChannelType.class, type)) {
            case PUBLIC -> {
                Iterable<Channel> channels = channelRepository.findAllPublic();
                yield StreamSupport.stream(channels.spliterator(), false)
                        .map(channel -> {
                            Instant lastMessageAt = messageRepository.maxCreatedAtByChannelId(channel.getId());
                            return ChannelMapper.toPublicResponseWithLastMessageAt(channel, lastMessageAt);
                        })
                        .collect(Collectors.toUnmodifiableSet());
            }
            case PRIVATE -> {
                Iterable<Channel> channels = channelRepository.findAllPrivate();
                yield StreamSupport.stream(channels.spliterator(), false)
                    .map(ChannelMapper::toPrivateResponse)
                    .collect(Collectors.toUnmodifiableSet());
            }
        };
    }

    @Override
    public PublicResponse updateChannelById(UUID id, PublicRequest request) {
        Channel channel = channelRepository.findPublicById(id);
        channel = channel.with(request.channelName(), request.description());
        channel = channelRepository.save(channel);
        return ChannelMapper.toPublicResponse(channel);
    }

    @Override
    public void deleteChannelById(UUID id) {
        Channel channel = channelRepository.findById(id);
        channelRepository.deleteById(channel.getId());
    }
}
