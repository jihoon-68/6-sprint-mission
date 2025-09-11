package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.model.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.response.ChannelFindAllResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    @Override
    public void createChannel(ChannelCreateRequest request) {
        if (request.getChannelName().trim().isEmpty()) {
            log.info("잘못된 ChannelName 형식 - ownerId: {}", request.getOwnerId());
            throw new IllegalArgumentException("ChannelName은 1글자 이상 입력해주세요.");
        }

        Channel channel = new Channel(request.getChannelName(), request.getOwnerId(), request.isPrivate());

        if (request.isPrivate()) {
            ReadStatus readStatus = new ReadStatus(channel.getId(), channel.getOwnerId());
            readStatusRepository.save(readStatus);
            log.info("ReadStatus 생성 - id: {}", readStatus.getId());
        }

        channelRepository.save(channel);
        log.info("Channel 생성 - id: {}", channel.getId());
    }

    @Override
    public void updateChannelName(ChannelUpdateRequest request) {
        Channel channel = channelRepository.findById(request.getChannelId());
        if (channel == null) {
            log.warn("Channel을 찾을 수 없습니다. - id: {}",  request.getChannelId());
            throw new IllegalArgumentException("Channel을 찾을 수 없습니다.");
        }

        if (request.getChannelName().trim().isEmpty()) {
            log.info("잘못된 channelName 형식 - channelId: {}", request.getChannelId());
            throw new IllegalArgumentException("channelName은 1글자 이상 입력해주세요.");
        }

        channel.updateChannelName(request.getChannelName());
        channelRepository.save(channel);
    }

    @Override
    public void deleteChannelById(UUID id) {
        Channel channel = channelRepository.findById(id);
        if (channel == null) {
            log.warn("Channel을 찾을 수 없습니다. - channelId: {}", id);
            throw new IllegalArgumentException("Channel을 찾을 수 없습니다.");
        }

        messageRepository.deleteByChannelId(id);
        readStatusRepository.deleteByChannelId(id);
        channelRepository.deleteById(id);
    }

    @Override
    public void deleteChannelByOwnerId(UUID ownerId) {
        List<UUID> ids = channelRepository.findAll()
                .stream().filter(channel -> Objects.equals(channel.getOwnerId(), ownerId))
                .map(Channel::getId).toList();

        for (UUID id : ids) {
            Channel channel = channelRepository.findById(id);

            messageRepository.deleteByChannelId(id);
            readStatusRepository.deleteByChannelId(id);
            channelRepository.deleteById(id);
        }
    }

    @Override
    public ChannelDto findByChannelId(UUID id) {
        Channel channel = channelRepository.findById(id);

        if (channel == null) {
            log.warn("Channel을 찾을 수 없습니다. -  channelId: {}", id);
            throw new IllegalArgumentException("Channel을 찾을 수 없습니다.");
        }

        List<Message> messages = messageRepository.findByChannelId(channel.getId());
        Instant messageCreateAt = null;
        if (messages != null && !messages.isEmpty()) {
            messageCreateAt = messages.get(messages.size() - 1).getCreateAt();
        }

        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelId(channel.getId());
        channelDto.setChannelName(channel.getChannelName());
        channelDto.setOwnerId(channel.getOwnerId());
        channelDto.setMemberIds(channel.getMemberIds());
        channelDto.setMessageCreateAt(messageCreateAt);
        channelDto.setPrivate(channel.isPrivate());

        return channelDto;
    }

    //사용자가 볼 수 있는 채널 목록
    @Override
    public ChannelFindAllResponse findAllByUserId(UUID userId) {
        List<Channel> channels = channelRepository.findAll();
        ChannelFindAllResponse response = new ChannelFindAllResponse();
        List<ChannelDto> channelDtos = new ArrayList<>();
        List<Message> messages;

        for (Channel channel : channels) {
            if (!channel.isPrivate() ||
                    channel.getOwnerId().equals(userId) ||
                    channel.getMemberIds().contains(userId)) {
                messages = messageRepository.findByChannelId(channel.getId());
                Instant messageCreateAt = null;

                if (messages != null && !messages.isEmpty()) {
                    messageCreateAt = messages.get(messages.size() - 1).getCreateAt();
                }

                ChannelDto channelDto = new ChannelDto();
                channelDto.setChannelId(channel.getId());
                channelDto.setOwnerId(channel.getOwnerId());
                channelDto.setMessageCreateAt(messageCreateAt);
                channelDto.setMemberIds(channel.getMemberIds());
                channelDto.setPrivate(channel.isPrivate());
                channelDto.setChannelName(channel.getChannelName());

                channelDtos.add(channelDto);
            }
        }

        response.setChannels(channelDtos);

        return response;
    }

    //사용자가 Owner로 있는 채널
    @Override
    public ChannelFindAllResponse findAllByOwnerId(UUID id) {
        //    private UUID channelId;
        //    private String channelName;
        //    private UUID ownerId;
        //    private Instant messageUpdateAt;
        //    private List<UUID> memberIds;

        List<Channel> channels = channelRepository.findAll();
        List<ChannelDto> channelDtos = new ArrayList<>();
        List<Message> messages;

        for (Channel channel : channels) {
            if (channel.getOwnerId().equals(id)) {
                messages = messageRepository.findByChannelId(channel.getId());
                Instant messageCreateAt = null;

                if (messages != null &&  !messages.isEmpty()) {
                    messageCreateAt = messages.get(messages.size() - 1).getCreateAt();
                }
                ChannelDto channelDto = new ChannelDto();
                channelDto.setChannelId(channel.getId());
                channelDto.setOwnerId(channel.getOwnerId());
                channelDto.setMessageCreateAt(messageCreateAt);
                channelDto.setMemberIds(channel.getMemberIds());

                channelDtos.add(channelDto);
            }
        }

        ChannelFindAllResponse response = new ChannelFindAllResponse();
        response.setChannels(channelDtos);

        return response;
    }
}
