package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.readstatusdto.CreateReadStatusDto;
import com.sprint.mission.discodeit.dto.readstatusdto.UpdateReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    public final ReadStatusRepository readStatusRepository;
    public final UserRepository userRepository;
    public final ChannelRepository channelRepository;

    @Override
    public ReadStatus create(CreateReadStatusDto createReadStatusDto) {
        if(userRepository.existsById(createReadStatusDto.userId())){
            throw new NoSuchElementException("유저가 없습니다: " + createReadStatusDto.userId());
        }
        if(channelRepository.existsById(createReadStatusDto.channelId())){
            throw new NoSuchElementException("채널이 없습니다: " + createReadStatusDto.channelId());
        }
        if(readStatusRepository.existsById(createReadStatusDto.readStatusId())){
            throw new IllegalArgumentException("이미 읽기상태 객체가 있습니다");
        }
        Instant now = Instant.now();
        ReadStatus readStatus = new ReadStatus(createReadStatusDto.userId(),createReadStatusDto.channelId(),now);
        return readStatusRepository.save(readStatus);
    }

    @Override
    public ReadStatus find(UUID readStatusId) {
        return readStatusRepository.findById(readStatusId)
                .orElseThrow(() -> new NoSuchElementException("ReadStatus with id " + readStatusId + " not found"));
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return readStatusRepository.findAllByUserId(userId);
    }

    @Override
    public ReadStatus update(UUID readStatusId, UpdateReadStatusDto updateReadStatusDto) {
        ReadStatus readStatus = readStatusRepository.findById(readStatusId)
                .orElseThrow(() -> new NoSuchElementException("ReadStatus with id " + updateReadStatusDto.userId() + " not found"));
        Instant now = Instant.now();
        readStatus.update(now);
        return readStatusRepository.save(readStatus);
    }

    @Override
    public void delete(UUID readStatusId) {
        if (!readStatusRepository.existsById(readStatusId)) {
            throw new NoSuchElementException("ReadStatus with id " + readStatusId + " not found");
        }
        readStatusRepository.deleteById(readStatusId);
    }
}
