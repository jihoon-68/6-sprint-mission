package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;


    @Override
    public ReadStatus create(CreateReadStatusDTO createReadStatusDTO) {

        User user = userRepository.findById(createReadStatusDTO.userId())
                .orElseThrow(()-> new IllegalArgumentException("User with id: " + createReadStatusDTO.userId() + " not found"));
        Channel channel = channelRepository.findById(createReadStatusDTO.channelId())
                .orElseThrow(()-> new IllegalArgumentException("Channel with id: " + createReadStatusDTO.channelId() + " not found"));

        boolean isDuplication = readStatusRepository.findAll().stream()
                .anyMatch(rs ->
                        rs.getUser().getId().equals(user.getId())
                                && rs.getChannel().getId().equals(channel.getId()));
        if(isDuplication) {
            throw new DuplicateFormatFlagsException("Duplicate Read Status");
        }


        return readStatusRepository.save(new ReadStatus(channel,user));
    }

    @Override
    public ReadStatus findById(UUID id) {
        return  readStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ReadStatus not found") );
    }


    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {

        return readStatusRepository.findAll().stream()
                .filter(sr -> sr.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public ReadStatus update(UUID readStatusId, Instant newLastReadAt) {
        ReadStatus readStatus = readStatusRepository.findById(readStatusId)
                .orElseThrow(() -> new NoSuchElementException("ReadStatus not found"));

        readStatus.update(newLastReadAt);
        return readStatusRepository.save(readStatus);
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.deleteById(id);
    }
}
