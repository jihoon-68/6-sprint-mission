package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.Status.CreateReadStatusRequest;
import com.sprint.mission.discodeit.DTO.Status.UpdateReadStatusRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
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
    public ReadStatus create(CreateReadStatusRequest request) {
        boolean isChannelExist = channelRepository.existsById(request.channelId());
        boolean isUserExist = userRepository.existsById(request.userId());

        if(isChannelExist == false || isUserExist == false) {
            throw new IllegalArgumentException("Invalid channel or user id");
        }

        List<ReadStatus> readStatuses = readStatusRepository
                .findByChannelId(request.channelId())
                .orElse(new ArrayList<>());

        if(readStatuses.isEmpty() == false) {
            long count = readStatuses.stream()
                    .filter(x->x.getUserId().equals(request.userId()))
                    .count();
            if(count != 0) {
                throw new IllegalArgumentException("ReadStatus already exists");
            }
        }
        ReadStatus readStatus = new ReadStatus(request.userId(), request.channelId());
        readStatusRepository.save(readStatus);

        return readStatus;
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
    public ReadStatus update(UpdateReadStatusRequest request) {
        ReadStatus readStatus = readStatusRepository.find(request.id())
                .orElseThrow(() -> new NoSuchElementException("ReadStatus not found with id " + request.id()));

        readStatus.update(request.userId(), request.channelId());
        readStatusRepository.save(readStatus);
        return readStatus;
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.delete(id);
    }
}
