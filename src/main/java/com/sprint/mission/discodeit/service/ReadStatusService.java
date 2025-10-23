package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequestDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReadStatusService {

    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ReadStatusMapper readStatusMapper;


    @Transactional
    public ReadStatusResponseDto create(ReadStatusCreateRequestDto dto){

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        Channel channel = channelRepository.findById(dto.channelId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다."));

        // 중복 체크
        List<ReadStatus> userReadStatuses = readStatusRepository.findAllByUserId(dto.userId());
        boolean exists = userReadStatuses.stream()
                .anyMatch(readStatus -> readStatus.getChannel().getId().equals(dto.channelId()));
        if (exists) {
            throw new IllegalArgumentException("해당 유저와 채널에 대해 이미 ReadStatus가 존재합니다.");
        }

        ReadStatus readStatus = ReadStatus.builder()
                .user(user)
                .channel(channel)
                .lastReadAt(null)
                .build();

        readStatusRepository.save(readStatus);

        return readStatusMapper.toDto(readStatus);
    }

    @Transactional(readOnly = true)
    public ReadStatusResponseDto findById(UUID id){
        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 ReadStatus입니다"));
        return readStatusMapper.toDto(readStatus);
    }

    @Transactional(readOnly = true)
    public List<ReadStatusResponseDto> findAllByUserId(UUID id){
        List<ReadStatus> readStatuses = readStatusRepository.findAllByUserId(id);
        return readStatuses.stream()
                .map(readStatusMapper::toDto)
                .toList();
    }

    @Transactional
    public ReadStatusResponseDto update(UUID id, ReadStatusUpdateRequestDto dto){
        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 ReadStatus입니다."));
        readStatus.setLastReadAt(dto.newLastReadAt());
        readStatusRepository.save(readStatus); // 명시적 저장
        return readStatusMapper.toDto(readStatus);
    }

    @Transactional
    public void deleteById(UUID id){
        readStatusRepository.deleteById(id);
        log.info("ReadStatus 삭제 완료: " + id);
    }

//    @Transactional
//    public void clear(){
//        readStatusRepository.clear();
//    }

}
