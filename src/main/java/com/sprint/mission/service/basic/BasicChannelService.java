package com.sprint.mission.service.basic;

import com.sprint.mission.dto.channel.ChannelCreateDto;
import com.sprint.mission.dto.channel.ChannelReturnDto;
import com.sprint.mission.dto.channel.ChannelUpdateDto;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.EntityCommon;
import com.sprint.mission.repository.ChannelRepository;
import com.sprint.mission.repository.MessageRepository;
import com.sprint.mission.repository.ReadStatusRepository;
import com.sprint.mission.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    @Override
    public Channel createPublic(ChannelCreateDto channelCreateDto) {
        if(channelCreateDto.getChannelName().isEmpty()){
            throw new IllegalArgumentException("Channel name cannot be empty");
        }
        return channelRepository.save(channelCreateDto);
    }

    @Override
    public Channel createPrivate(ChannelCreateDto channelCreateDto) {
        if(channelCreateDto.getChannelName().isEmpty()){
            throw new IllegalArgumentException("Channel name cannot be empty");
        }
        if(channelCreateDto.getChannelMembers().isEmpty()){
            throw new IllegalArgumentException("Private channel must have at least one member");
        }
        return channelRepository.save(channelCreateDto);
    }

    @Override
    public Channel find(UUID id) {
        return channelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Channel not found"));
    }

    @Override
    public Map<String, List<ChannelReturnDto>> findAllByUserId(UUID userId) {
        List<Channel> channels = channelRepository.findAll();
        Map<String, List<ChannelReturnDto>> returnMap = new HashMap<>();
        List<ChannelReturnDto> publicChannelList = channels.stream()
                .filter(channel -> channel.getChannelType().equals("PUBLIC"))
                .map(channel -> new ChannelReturnDto(
                        channel.getChannelName(),
                        channel.getChannelDescription(),
                        channel.getChannelType(), null,
                        channel.getUpdatedAt())
                )
                .toList();
        returnMap.put("publicChannelList", publicChannelList);
        List<ChannelReturnDto> privateChannelList = channels.stream()
                .filter(channel -> channel.getChannelType().equals("PRIVATE")
                        && channel.getMembers().stream().anyMatch(user -> user.getId().equals(userId)))
                .map(channel -> new ChannelReturnDto(
                        channel.getChannelName(),
                        channel.getChannelDescription(),
                        channel.getChannelType(),
                        channel.getMembers().stream().map(EntityCommon::getId).toList(),
                        channel.getUpdatedAt())
                )
                .toList();
        returnMap.put("privateChannelList", privateChannelList);
        return returnMap;
    }

    @Override
    public void update(UUID id, ChannelUpdateDto channelUpdateDto) {

    }

    @Override
    public void delete(UUID id) {
        if(channelRepository.findById(id).isPresent()){
            channelRepository.deleteById(id);
            messageRepository.deleteByChannelId(id);
            readStatusRepository.deleteByChannelId(id);
        }
    }
}
