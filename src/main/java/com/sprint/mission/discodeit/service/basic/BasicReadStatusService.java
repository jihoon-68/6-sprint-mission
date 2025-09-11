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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;


    @Override
    public ReadStatus create(CreateReadStatusDTO createReadStatusDTO) {
        // 예외 처리 뭉처서 말고 개별처리
        if(userRepository.findById(createReadStatusDTO.userId()).isEmpty()) {
            throw new NullPointerException("User not found");
        }

        if(channelRepository.findById(createReadStatusDTO.channelId()).isEmpty()) {
            throw new NullPointerException("Channel not found");
        }

        boolean isDuplication = readStatusRepository.findAll().stream()
                .anyMatch(rs -> rs.getUserId().equals(createReadStatusDTO.userId())
                && rs.getChannelId().equals(createReadStatusDTO.channelId()));
        if(isDuplication) {
            throw new IllegalStateException("Duplicate Read Status");
        }


        return readStatusRepository.save(new ReadStatus(createReadStatusDTO));
    }

    @Override
    public ReadStatus findById(UUID id) {
        return  readStatusRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("ReadStatus not found") );
    }


    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {

        return readStatusRepository.findAll().stream()
                .filter(sr -> sr.getUserId().equals(userId))
                .toList();
    }

    @Override
    public void update(UpdateReadStatusDTO updateReadStatusDTO) {
        ReadStatus readStatus = findById(updateReadStatusDTO.id());
        readStatus.update(updateReadStatusDTO);
        readStatusRepository.save(readStatus);
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.delete(id);
    }
}
