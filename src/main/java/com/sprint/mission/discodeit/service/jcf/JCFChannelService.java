package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.dto.channel.model.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.response.ChannelFindAllResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JCFChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    @Override
    public void createChannel(ChannelCreateRequest request) {
        if (request.getChannelName().trim().isEmpty()) {
            System.out.println("[Error] 채널 이름은 1글자 이상 입력해주세요.");
            return;
        }

        Channel channel = new Channel(request.getChannelName(), request.getOwnerId(), request.isPrivate());

        if (request.isPrivate()) {
            ReadStatus readStatus = new ReadStatus(channel.getId(), channel.getOwnerId());
            readStatusRepository.save(readStatus);
        }

        channelRepository.save(channel);
        System.out.println("[Info] 채널이 생성되었습니다.");
    }

    @Override
    public void updateChannelName(ChannelUpdateRequest request) {
        Channel channel = channelRepository.findById(request.getChannelId());
        if (channel == null) {
            System.out.println("[Error] 존재하지 않는 채널입니다.");
            return;
        }

        if (request.getChannelName().trim().isEmpty()) {
            System.out.println("[Error] 채널 이름은 1글자 이상 입력해주세요.");
            return;
        }

        channel.updateChannelName(request.getChannelName());
        channelRepository.save(channel);

        System.out.println("[Info] 채널명이 수정되었습니다.");
    }

    @Override
    public void deleteChannelById(UUID id) {
        if (channelRepository.existsById(id)) {
            channelRepository.deleteById(id);
            System.out.println("[Info] 채널 삭제가 완료되었습니다.");
        } else {
            System.out.println("[Error] 채널 삭제에 실패했습니다.");
        }
    }

    @Override
    public void deleteChannelByOwnerId(UUID ownerId) {
        List<UUID> ids = channelRepository.findAll()
                .stream().filter(c -> c.getOwnerId().equals(ownerId))
                .map(Channel::getId).toList();

        for (UUID id : ids) {
            channelRepository.deleteById(id);
        }
    }

    @Override
    public ChannelDto findByChannelId(UUID id) {
        Channel channel = channelRepository.findById(id);

        if (channel == null) {
            System.out.println("[Error] 채널을 찾을 수 없습니다.");
            return null;
        }

        List<Message> messages = messageRepository.findByChannelId(channel.getId());
        Instant messageCreateAt = null;
        if (messages != null) {
            messageCreateAt = messages.get(messages.size() - 1).getCreateAt();
        }

        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelId(channel.getId());
        channelDto.setChannelName(channel.getChannelName());
        channelDto.setOwnerId(channel.getOwnerId());
        channelDto.setMemberIds(channel.getMemberIds());
        channelDto.setMessageCreateAt(messageCreateAt);

        return channelDto;
    }

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

                if (messages != null) {
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

        response.setChannels(channelDtos);

        return response;
    }

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

                if (messages != null) {
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
