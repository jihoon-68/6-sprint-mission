package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequestDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.channel.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.readstatus.ReadStatusAlreadyExistsException;
import com.sprint.mission.discodeit.exception.readstatus.ReadStatusListNotFoundException;
import com.sprint.mission.discodeit.exception.readstatus.ReadStatusNotFoundException;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException(dto.userId()));
        Channel channel = channelRepository.findById(dto.channelId())
                .orElseThrow(() -> new ChannelNotFoundException(dto.channelId()));

        // 중복 체크
        List<ReadStatus> userReadStatuses = readStatusRepository.findAllByUserId(dto.userId());
        boolean exists = userReadStatuses.stream()
                .anyMatch(readStatus -> readStatus.getChannel().getId().equals(dto.channelId()));
        if (exists) {
            throw new ReadStatusAlreadyExistsException(channel.getId(), user.getId());
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
                .orElseThrow(() -> new ReadStatusNotFoundException(id));
        return readStatusMapper.toDto(readStatus);
    }

    @Transactional(readOnly = true)
    public List<ReadStatusResponseDto> findAllByUserId(UUID id){
        List<ReadStatus> readStatuses = readStatusRepository.findAllByUserId(id);

        if (readStatuses.isEmpty()) {
            throw new ReadStatusListNotFoundException(id);
        }

        return readStatuses.stream()
                .map(readStatusMapper::toDto)
                .toList();
    }

    @Transactional
    public ReadStatusResponseDto update(UUID id, ReadStatusUpdateRequestDto dto){
        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new ReadStatusNotFoundException(id));
        readStatus.setLastReadAt(dto.newLastReadAt());
        readStatusRepository.save(readStatus);
        return readStatusMapper.toDto(readStatus);
    }

    @Transactional
    public void deleteById(UUID id){
        ReadStatus readStatus = readStatusRepository.findById(id)
                        .orElseThrow(() -> new ReadStatusNotFoundException(id));
        readStatusRepository.deleteById(id);
        log.info("ReadStatus 삭제 완료: " + id);
    }

//    @Transactional
//    public void clear(){
//        readStatusRepository.clear();
//    }

}
