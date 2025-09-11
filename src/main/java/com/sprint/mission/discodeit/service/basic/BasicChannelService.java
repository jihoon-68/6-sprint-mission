package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channeldto.CreateChannelDto;
import com.sprint.mission.discodeit.dto.channeldto.FindAllChannelDto;
import com.sprint.mission.discodeit.dto.channeldto.FindChannelDto;
import com.sprint.mission.discodeit.dto.channeldto.UpdateChannelDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Channel create(CreateChannelDto createChannelDto) {
        Channel channel;
        if(createChannelDto.type().equals(ChannelType.PUBLIC)){
            channel = new Channel(ChannelType.PUBLIC, createChannelDto.name(), createChannelDto.description());
        }
        else {
            channel = new Channel(createChannelDto.type(), null, null);
            for (UUID userId : createChannelDto.userIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("유저 없음: " + userId));
                channel.addUser(user);
                readStatusRepository.save(new ReadStatus(userId, channel.getId()));
            }
        }
        return channelRepository.save(channel);
    }

    @Override
    public Channel find(FindChannelDto findChannelDto) {
        return channelRepository.findById(findChannelDto.channelId())
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + findChannelDto.channelId() + " not found"));
    }

    @Override
    public List<Channel> findAllByUserId(FindAllChannelDto findAllChannelDto) {
        if(findAllChannelDto.type().equals(ChannelType.PRIVATE)){
            return channelRepository.findAll().stream().filter(channel -> channel.getUserIds().contains(findAllChannelDto.userId())).toList();
        }
        return channelRepository.findAll();
    }

    @Override
    public Channel update(UpdateChannelDto updateChannelDto) {
        if (updateChannelDto.type().equals(ChannelType.PRIVATE)) {
            System.out.println(" PRIVATE 채널은 수정할 수 없습니다.");
            return null;
        }
        Channel channel = channelRepository.findById(updateChannelDto.channelId())
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + updateChannelDto.channelId() + " not found"));

        channel.update(updateChannelDto.newName(), updateChannelDto.newDescription());
        return channelRepository.save(channel);
    }

    @Override
    public void delete(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }
        // 같은 채널에서 나온 읽기상태들 삭제
        List<ReadStatus> readStatusesInChannel = readStatusRepository.findAll().stream().filter(readStatus -> readStatus.getChannelId().equals(channelId)).toList();
        for(ReadStatus readStatus: readStatusesInChannel){
            readStatusRepository.deleteById(readStatus.getId());
        }
        // 같은 채널에서 나온 메시지들 삭제
        List<Message> messagesInChannel = messageRepository.findAll().stream().filter(msg -> msg.getChannelId().equals(channelId)).toList();
        for(Message message: messagesInChannel){
            messageRepository.deleteById(message.getId());
        }
        // 채널 id로 삭제
        channelRepository.deleteById(channelId);


    }
}
