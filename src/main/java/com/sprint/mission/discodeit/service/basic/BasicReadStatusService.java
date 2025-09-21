package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.readstatusdto.CreateReadStatus;
import com.sprint.mission.discodeit.dto.readstatusdto.UpdateReadStatus;
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
    public ReadStatus create(CreateReadStatus createReadStatus) {
        if(userRepository.existsById(createReadStatus.userId())){
            throw new NoSuchElementException("유저가 없습니다: " + createReadStatus.userId());
        }
        if(channelRepository.existsById(createReadStatus.channelId())){
            throw new NoSuchElementException("채널이 없습니다: " + createReadStatus.channelId());
        }
        if(readStatusRepository.existsById(createReadStatus.readStatusId())){
            throw new IllegalArgumentException("이미 읽기상태 객체가 있습니다");
        }
        ReadStatus readStatus = new ReadStatus(createReadStatus.userId(), createReadStatus.channelId(),Instant.now());
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
    public ReadStatus update(UUID readStatusId, UpdateReadStatus updateReadStatus) {
        ReadStatus readStatus = readStatusRepository.findById(readStatusId)
                .orElseThrow(() -> new NoSuchElementException("ReadStatus with id " + updateReadStatus.userId() + " not found"));
        readStatus.update(Instant.now());
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
