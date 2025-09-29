package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.Channel.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.dto.Channel.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.dto.Channel.FindChannelDTO;
import com.sprint.mission.discodeit.dto.Channel.UpdateChannelDTO;
import com.sprint.mission.discodeit.enumtype.ChannelType;
import com.sprint.mission.discodeit.entity.Channel;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    @Override
    public Channel createPublic(CreatePublicChannelDTO createPublicChannelDTO) {

        return channelRepository.save(new Channel(createPublicChannelDTO.name(), createPublicChannelDTO.description()));
    }

    @Override
    public Channel createPrivate(CreatePrivateChannelDTO createPrivateChannelDTO) {
        Channel channel = new Channel();

        createPrivateChannelDTO.participantIds()
                .forEach(userId ->
                        readStatusRepository.save(new ReadStatus(channel.getId(), userId)));

        return channelRepository.save(channel);
    }

    @Override
    public FindChannelDTO find(UUID id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Channel not found"));

        Message message = messageRepository.findAll().stream()
                .filter(m -> m.getChannelId().equals(channel.getId()))
                .findFirst().orElseThrow(() -> new NoSuchElementException("message not found"));

        if (channel.getType().equals(ChannelType.PRIVATE)) {
            List<UUID> userIds = readStatusRepository.findAll().stream()
                    .map(ReadStatus::getUserId)
                    .toList();
            return new FindChannelDTO(channel, message.getCreatedAt(), userIds);
        }

        return FindChannelDTO.createPublicChannelDto(channel, message.getCreatedAt());
    }

    @Override
    public List<FindChannelDTO> findAllByUserId(UUID userId) {

        //공개 채널부터 다넣음
        List<Channel> channels = channelRepository.findAll().stream()
                .filter(c -> c.getType().equals(ChannelType.PUBLIC))
                .collect(Collectors.toList());

        //유저 읽음 싱테 들고옴
        List<ReadStatus> readStatusesUser = readStatusRepository.findAll().stream()
                .filter(rs -> rs.getUserId().equals(userId))
                .toList();

        //리드 상태로 비공개 채널 전채 채널 리스트에 추가
        for (ReadStatus readStatuses : readStatusesUser) {
            Channel channel = channelRepository.findById(readStatuses.getChannelId())
                    .orElseThrow(() -> new NoSuchElementException("channel not found"));
            if (channel.getType().equals(ChannelType.PUBLIC)) {continue;}
            channels.add(channel);
        }

        //채널별 최근채팅시간 연결
        List<FindChannelDTO> findChannelDTOS = new ArrayList<>();
        for (Channel channel : channels) {
            Instant messageTime = messageRepository.findAll().stream()
                    .filter(m -> m.getChannelId().equals(channel.getId()))
                    .map(Message::getCreatedAt)
                    .findFirst().orElse(channel.getCreatedAt());

            if (channel.getType().equals(ChannelType.PRIVATE)) {
                List<UUID> userIds = readStatusRepository.findAll().stream()
                        .map(ReadStatus::getUserId)
                        .toList();

                findChannelDTOS.add(new  FindChannelDTO(channel, messageTime,userIds));
                continue;
            }
            findChannelDTOS.add(FindChannelDTO.createPublicChannelDto(channel, messageTime));
        }


        return List.copyOf(findChannelDTOS);
    }

    @Override
    public Channel update(UUID channelID, UpdateChannelDTO updateChannelDTO) {
        Channel channel = channelRepository.findById(channelID)
                .orElseThrow(() -> new NoSuchElementException("Channel not found"));

        if (channel.getType().equals(ChannelType.PRIVATE)) {
            throw new UnsupportedOperationException("Private channel not supported");
        }
        channel.update(updateChannelDTO.newName(), updateChannelDTO.newDescription());
        return channelRepository.save(channel);
    }

    @Override
    public void delete(UUID channelId) {
        readStatusRepository.findAll().stream()
                .filter(rs -> rs.getChannelId().equals(channelId))
                .forEach(rsDelete -> readStatusRepository.deleteById(rsDelete.getId()));

        messageRepository.findAll().stream()
                .filter(m -> m.getChannelId().equals(channelId))
                .forEach(mDelete -> readStatusRepository.deleteById(mDelete.getId()));

        channelRepository.deleteById(channelId);
    }
}
