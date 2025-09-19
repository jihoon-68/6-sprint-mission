package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequestDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReadStatusService {

    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public ReadStatusResponseDto create(ReadStatusCreateRequestDto dto){
        if (userRepository.findById(dto.userId()) == null) {
            throw new NoSuchElementException("존재하지 않는 유저입니다.");
        }
        if (channelRepository.findById(dto.channelId()) == null) {
            throw new NoSuchElementException("존재하지 않는 채널입니다.");
        }
        // 중복 체크
        List<ReadStatus> userReadStatuses = readStatusRepository.findAllByUserId(dto.userId());
        boolean exists = userReadStatuses.stream()
                .anyMatch(rs -> rs.getChannelId().equals(dto.channelId()));
        if (exists) {
            throw new IllegalArgumentException("해당 유저와 채널에 대해 이미 ReadStatus가 존재합니다.");
        }

        ReadStatus readStatus = new ReadStatus(dto.userId(), dto.channelId());
        readStatusRepository.save(readStatus);
        return new ReadStatusResponseDto(
                readStatus.getId(),
                readStatus.getUserId(),
                readStatus.getChannelId(),
                readStatus.getLastlyReadAt()
        );
    }

    public ReadStatusResponseDto findById(UUID id){
        ReadStatus readStatus = readStatusRepository.findById(id);
        return new ReadStatusResponseDto(
                readStatus.getId(),
                readStatus.getUserId(),
                readStatus.getChannelId(),
                readStatus.getLastlyReadAt()
        );
    }

    public List<ReadStatusResponseDto> findAllByUserId(UUID id){
        List<ReadStatus> readStatuses = readStatusRepository.findAllByUserId(id);
        return readStatuses.stream()
                .map(rs -> new ReadStatusResponseDto(
                        rs.getId(),
                        rs.getUserId(),
                        rs.getChannelId(),
                        rs.getLastlyReadAt()))
                .toList();
    }

    public ReadStatusResponseDto update(ReadStatusUpdateRequestDto dto){
        ReadStatus readStatus = readStatusRepository.findById(dto.id());
        readStatusRepository.save(readStatus);
        return new ReadStatusResponseDto(
                readStatus.getId(),
                readStatus.getUserId(),
                readStatus.getChannelId(),
                readStatus.getLastlyReadAt()
        );
    }

    public void deleteById(UUID id){
        readStatusRepository.deleteById(id);
        log.info("ReadStatus 삭제 완료: " + id);
    }

    public void clear(){
        readStatusRepository.clear();
    }

}
