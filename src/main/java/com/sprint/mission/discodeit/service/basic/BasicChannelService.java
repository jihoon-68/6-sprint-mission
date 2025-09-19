package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channeldto.CreateChannel;
import com.sprint.mission.discodeit.dto.channeldto.UpdateChannel;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public Channel create(CreateChannel createChannel) {
        Channel channel;
        if(createChannel.type().equals(ChannelType.PUBLIC)){
            channel = new Channel(ChannelType.PUBLIC, createChannel.name(), createChannel.description());
        }
        else {
            channel = new Channel(createChannel.type(), null, null);
            for (UUID userId : createChannel.userIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("유저 없음: " + userId));
                Instant now = Instant.now();
                readStatusRepository.save(new ReadStatus(userId, channel.getId(),now));
            }
        }
        return channelRepository.save(channel);
    }

    @Override
    public Channel find(UUID channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));
    }

    @Override
    public List<Channel> findAllByUserId(UUID userId) {
        List<UUID> mySubscribedChannelIds = readStatusRepository.findAllByUserId(userId).stream()
                .map(ReadStatus::getChannelId)
                .toList();

        return channelRepository.findAll().stream()
                .filter(channel ->
                        channel.getType().equals(ChannelType.PUBLIC)
                                || mySubscribedChannelIds.contains(channel.getId())
                ).toList();
    }

    @Override
    public Channel update(UUID channelId, UpdateChannel updateChannel) {
        if (updateChannel.type().equals(ChannelType.PRIVATE)) {
            System.out.println(" PRIVATE 채널은 수정할 수 없습니다.");
            return null;
        }
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));

        channel.update(updateChannel.newName(), updateChannel.newDescription());
        return channelRepository.save(channel);
    }

    @Override
    public void delete(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }
        // 같은 채널에서 나온 읽기상태들 삭제
        List<ReadStatus> readStatusesInChannel = readStatusRepository.findAllByChannelId(channelId);
        for(ReadStatus readStatus: readStatusesInChannel){
            readStatusRepository.deleteById(readStatus.getId());
        }
        // 같은 채널에서 나온 메시지들 삭제
        List<Message> messagesInChannel = messageRepository.findAllByChannelId(channelId);
        for(Message message: messagesInChannel){
            messageRepository.deleteById(message.getId());
        }
        // 채널 id로 삭제
        channelRepository.deleteById(channelId);
    }
}
