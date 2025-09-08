package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTOs.BinaryContent.CreateBinaryContentDTO;
import com.sprint.mission.discodeit.DTOs.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.DTOs.ReadStatus.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public BasicReadStatusService(ReadStatusRepository readStatusRepository,
                                  UserRepository userRepository,
                                  ChannelRepository channelRepository) {
     this.readStatusRepository = readStatusRepository;
     this.userRepository = userRepository;
     this.channelRepository = channelRepository;
    }

    @Override
    public ReadStatus create(CreateReadStatusDTO createReadStatusDTO) {
        boolean isChannelExsist = channelRepository.existsById(createReadStatusDTO.channelId());
        boolean isUserExsist = userRepository.existsById(createReadStatusDTO.userId());

        if(isChannelExsist == false && isUserExsist == false){
            throw new NoSuchElementException("Channel or User is not exsist");
        }

        List<ReadStatus> readStatuses = readStatusRepository

                .findByChannelId(createReadStatusDTO.channelId())
                .orElse(new ArrayList<>());

        if(readStatuses.isEmpty() == false){
            long count = readStatuses.stream().filter(x -> x.getUserID()
                            .equals(createReadStatusDTO.userId()))
                    .count();

            if(count != 0){
                throw new IllegalStateException("Read Status is already exist");
            }
        }

        ReadStatus status = new ReadStatus(createReadStatusDTO.userId(), createReadStatusDTO.channelId());
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
    public ReadStatus update(UpdateReadStatusDTO updateReadStatusDTO) {
        ReadStatus status = readStatusRepository
                .find(updateReadStatusDTO.id())
                .orElseThrow(() -> new NoSuchElementException("Read Status not found"));

        status.update(updateReadStatusDTO.userId(), updateReadStatusDTO.channelId());
        readStatusRepository.save(status);
        return status;
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.delete(id);
    }
}
