package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatus.CreateReadStatusDto;
import com.sprint.mission.discodeit.dto.ReadStatus.UpdateReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ReadStatus create(CreateReadStatusDto createReadStatusDto) {
        boolean isChannelExsist = channelRepository.existsById(createReadStatusDto.channelId());
        boolean isUserExsist = userRepository.existsById(createReadStatusDto.userId());

        if(isChannelExsist == false && isUserExsist == false){
            throw new NoSuchElementException("Channel or User is not exsist");
        }

        List<ReadStatus> readStatuses = readStatusRepository
                .findByChannelId(createReadStatusDto.channelId())
                .orElse(new ArrayList<>());

        if(readStatuses.isEmpty() == false){
            long count = readStatuses.stream().filter(x -> x.getUserId()
                    .equals(createReadStatusDto.userId()))
                    .count();

            if(count != 0){
                throw new IllegalStateException("Read Status is already exist");
            }
        }

        ReadStatus status = new ReadStatus(createReadStatusDto.userId(), createReadStatusDto.channelId());
        readStatusRepository.save(status);

        return status;
    }

    @Override
    public ReadStatus find(UUID id) {
        return readStatusRepository.find(id).orElse(null);
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return readStatusRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public ReadStatus update(UpdateReadStatusDto updateReadStatusDto) {
        ReadStatus status = readStatusRepository
                .find(updateReadStatusDto.id())
                .orElseThrow(() -> new NoSuchElementException("Read Status not found"));

        status.update(updateReadStatusDto.userId(), updateReadStatusDto.channelId());
        readStatusRepository.save(status);
        return status;
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.delete(id);
    }
}
