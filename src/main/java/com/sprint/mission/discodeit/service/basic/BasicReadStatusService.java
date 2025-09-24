package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.DTO.ReadStatus.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        if(!userRepository.existsById(createReadStatusDTO.userId())
                ||!channelRepository.existsById(createReadStatusDTO.channelId())){
            throw new NoSuchElementException("User Or Channel channels are mandatory");
        }

        boolean isDuplication = readStatusRepository.findAll().stream()
                .anyMatch(rs ->
                        rs.getUserId().equals(createReadStatusDTO.userId())
                                && rs.getChannelId().equals(createReadStatusDTO.channelId()));
        if(isDuplication) {
            throw new DuplicateFormatFlagsException("Duplicate Read Status");
        }


        return readStatusRepository.save(new ReadStatus(createReadStatusDTO.channelId(),createReadStatusDTO.userId()));
    }

    @Override
    public ReadStatus findById(UUID id) {
        return  readStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ReadStatus not found") );
    }


    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {

        return readStatusRepository.findAll().stream()
                .filter(sr -> sr.getUserId().equals(userId))
                .toList();
    }

    @Override
    public void update(UpdateReadStatusDTO updateReadStatusDTO) {
        ReadStatus readStatus = readStatusRepository.findById(updateReadStatusDTO.id())
                .orElseThrow(() -> new NoSuchElementException("ReadStatus not found") );

        readStatus.update(updateReadStatusDTO);
        readStatusRepository.save(readStatus);
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.deleteById(id);
    }
}
