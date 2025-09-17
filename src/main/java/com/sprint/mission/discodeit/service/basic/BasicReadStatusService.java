package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.readStatus.model.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readStatus.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readStatus.response.ReadStatusFindAllByUserIdResponse;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;

    @Override
    public void createReadStatus(ReadStatusCreateRequest request) {
        if (userRepository.findById(request.getUserId()) == null) {
            log.warn("User를 찾을 수 없습니다. - userId: {}", request.getUserId());
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        if (channelRepository.findById(request.getChannelId()) == null) {
            log.warn("Channel을 찾을 수 없습니다. - channelId: {}", request.getChannelId());
            throw new IllegalArgumentException("Channel을 찾을 수 없습니다.");
        }

        ReadStatus conflictStatus = readStatusRepository.findAllByUserId(request.getUserId())
                .stream().filter(readStatus -> readStatus.getChannelId().equals(request.getChannelId())).findFirst().orElse(null);

        if (conflictStatus != null) {
            log.warn("이미 존재하는 ReadStatus - userId: {}, channelId: {}, conflictReadStatusId: {}", request.getUserId(), request.getChannelId(),  conflictStatus.getId());
            throw new IllegalStateException("이미 존재하는 ReadStatus입니다.");
        }

        ReadStatus readStatus = new ReadStatus(request.getUserId(), request.getChannelId());
        readStatusRepository.save(readStatus);
    }

    @Override
    public ReadStatusDto findOne(UUID id) {
        ReadStatus readStatus = readStatusRepository.findById(id);
        boolean read;

        if (readStatus == null) {
            log.warn("ReadStatus를 찾을 수 없습니다. - readStatusId: {}", id);
            throw new IllegalArgumentException("ReadStatus를 찾을 수 없습니다.");
        }

        List<Message> messages = messageRepository.findByChannelId(readStatus.getChannelId());

        if (messages == null || messages.isEmpty()) {
            read = true;
        } else {

            Message message = messages.get(messages.size() - 1);
            read = message.getCreateAt().isAfter(readStatus.getReadAt());
        }

        ReadStatusDto readStatusDto = new ReadStatusDto();
        readStatusDto.setId(readStatus.getId());
        readStatusDto.setUserId(readStatus.getUserId());
        readStatusDto.setChannelId(readStatus.getChannelId());
        readStatusDto.setRead(read);

        return readStatusDto;
    }

    @Override
    public ReadStatusDto findOneByUserIdAndChannelId(UUID userId, UUID channelId) {
        ReadStatus foundStatus = readStatusRepository.findAllByUserId(userId)
                .stream().filter(readStatus -> Objects.equals(readStatus.getChannelId(), channelId)).findFirst().orElse(null);

        if (foundStatus == null) {
            log.warn("ReadStatus를 찾을 수 없습니다. - userId: {}, channelId: {}", userId, channelId);
            throw new IllegalArgumentException("ReadStatus를 찾을 수 없습니다.");
        }

        boolean read;

        List<Message> messages = messageRepository.findByChannelId(foundStatus.getChannelId());

        if (messages == null || messages.isEmpty()) {
            read = true;
        } else {
            Message message = messages.get(messages.size() - 1);
            read = message.getCreateAt().isAfter(foundStatus.getReadAt());
        }

        ReadStatusDto readStatusDto = new ReadStatusDto();
        readStatusDto.setId(foundStatus.getId());
        readStatusDto.setUserId(foundStatus.getUserId());
        readStatusDto.setChannelId(foundStatus.getChannelId());
        readStatusDto.setRead(read);

        return readStatusDto;
    }

    @Override
    public ReadStatusFindAllByUserIdResponse findAllByUserId(UUID userId) {
        List<ReadStatusDto> readStatusDtoList = new ArrayList<>();
        List<ReadStatus> readStatuses = readStatusRepository.findAllByUserId(userId);

        if (readStatuses == null || readStatuses.isEmpty()) {
            log.warn("ReadStatus를 찾을 수 없습니다. - userId: {}", userId);
            throw new IllegalArgumentException("ReadStatus를 찾을 수 없습니다.");
        }

        boolean read;

        for  (ReadStatus readStatus : readStatuses) {
            List<Message> messages = messageRepository.findByChannelId(readStatus.getChannelId());

            if (messages == null || messages.isEmpty()) {
                read = true;
            } else {
                Message message = messages.get(messages.size() - 1);
                read = message.getCreateAt().isAfter(readStatus.getReadAt());
            }

            ReadStatusDto readStatusDto = new ReadStatusDto();
            readStatusDto.setId(readStatus.getId());
            readStatusDto.setUserId(readStatus.getUserId());
            readStatusDto.setChannelId(readStatus.getChannelId());
            readStatusDto.setRead(read);
            readStatusDtoList.add(readStatusDto);
        }

        ReadStatusFindAllByUserIdResponse response = new ReadStatusFindAllByUserIdResponse();
        response.setReadStatusDtoList(readStatusDtoList);

        return response;
    }

    @Override
    public void deleteOne(UUID id) {
        ReadStatus readStatus = readStatusRepository.findById(id);
        if (readStatus == null) {
            log.warn("ReadStatus를 찾을 수 없습니다. -  readStatusId: {}", id);
            throw new IllegalArgumentException("ReadStatus를 찾을 수 없습니다.");
        }

        readStatusRepository.deleteById(id);
    }

    @Override
    public void updateReadAt(UUID channelId, UUID userId) {
        ReadStatus targetStatus = readStatusRepository.findAllByUserId(userId)
                .stream().filter(readStatus -> readStatus.getChannelId().equals(channelId)).findFirst().orElse(null);

        if (targetStatus == null) {
           log.warn("ReadStatus를 찾을 수 없습니다. - userId: {}, channelId: {}", userId, channelId);
           throw new IllegalArgumentException("ReadStatus를 찾을 수 없습니다.");
       }
        readStatusRepository.save(targetStatus);
    }
}
