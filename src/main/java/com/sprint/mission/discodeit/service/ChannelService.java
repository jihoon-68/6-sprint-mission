package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequestDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;

    // 채널 생성 및 저장
    public ChannelResponseDto createPrivateChannel(PrivateChannelCreateRequestDto dto) {
        Channel channel = new Channel(dto.userId());

        // ReadStatus 생성
        if (dto.participants() != null) {
            dto.participants()
                    .forEach(userId ->
                            readStatusRepository.save(new ReadStatus(userId, channel.getId())));
        }

        channelRepository.save(channel);
        log.info("채널 추가 완료: " + channel.getName());
        return new ChannelResponseDto(
                channel.getId(),
                null, // private 채널은 name이 없음.
                null, // private 채널은 description이 없음.
                null,
                channel.getParticipants()
                );
    }

    public ChannelResponseDto createPublicChannel(PublicChannelCreateRequestDto dto) {
        Channel channel = new Channel(dto.userId());

        channel.setName(dto.name());

        if (dto.description() != null) {
            channel.setDescription(dto.description());
        }

        channelRepository.save(channel);
        log.info("채널 추가 완료: " + channel.getName());
        return new ChannelResponseDto(
                channel.getId(),
                channel.getName(),
                channel.getDescription(),
                null,
                null // public 채널은 participants가 없음.
        );
    }

    public ChannelResponseDto findById(UUID id) {
        Channel channel = channelRepository.findById(id);

        if (channel == null) {
            throw new NotFoundException("존재하지 않는 채널입니다.");
        }

        if (channel.getChannelType() == ChannelType.PRIVATE) {
            return new ChannelResponseDto(
                    channel.getId(),
                    null, // private 채널은 name이 없음.
                    null, // private 채널은 description이 없음.
                    latestMessageAddedAt(id),
                    channel.getParticipants());
        }
        else return new ChannelResponseDto(
                channel.getId(),
                channel.getName(),
                channel.getDescription(),
                latestMessageAddedAt(id),
                null // public 채널은 participants가 없음.
                );
    }

    // 전체 PUBLIC, 참여중인 PRIVATE 채널
    public List<ChannelResponseDto> findAllByUserId(UUID id) {
        List<Channel> channels = channelRepository.findAll();

        return channels.stream()
                .filter(channel ->
                        channel.getChannelType() == ChannelType.PUBLIC ||
                                channel.getParticipants().contains(id)
                )
                .map(channel -> {
                    if (channel.getChannelType() == ChannelType.PRIVATE) {
                        return new ChannelResponseDto(
                                channel.getId(),
                                null,
                                null,
                                latestMessageAddedAt(channel.getId()),
                                channel.getParticipants()
                        );
                    } else {
                        return new ChannelResponseDto(
                                channel.getId(),
                                channel.getName(),
                                channel.getDescription(),
                                latestMessageAddedAt(channel.getId()),
                                null
                        );
                    }
                })
                .toList();
    }


    public ChannelResponseDto update(UUID id, ChannelUpdateRequestDto dto) {
        // validateCreator(user, channel);
        Channel channel = channelRepository.findById(id);

        if (channel == null) {
            throw new NotFoundException("존재하지 않는 채널입니다.");
        }

        if (channel.getChannelType() == ChannelType.PRIVATE) {
            throw new IllegalStateException("PRIVATE 채널은 수정할 수 없습니다.");
        }

        if (dto.name() != null) {
            channel.setName(dto.name());
        };

        if (dto.description() != null) {
            channel.setDescription(dto.description());
        }

        channelRepository.save(channel);
        log.info("수정 및 저장 완료");

        if (channel.getChannelType() == ChannelType.PRIVATE) {
            return new ChannelResponseDto(
                    channel.getId(),
                    null,
                    null,
                    latestMessageAddedAt(id),
                    channel.getParticipants()
            );
        }
        else {
            return new ChannelResponseDto(
                    channel.getId(),
                    channel.getName(),
                    channel.getDescription(),
                    latestMessageAddedAt(id),
                    null
            );
        }
    }

    // 채널 삭제
    public void deleteById(UUID id) {
        // validateCreator(user, channel); // 만든사람만 삭제 가능

        Channel channel = channelRepository.findById(id);
        if (channel == null) {
            throw new NotFoundException("존재하지 않는 채널입니다.");
        }

        List<Message> messages = messageRepository.findByChannelId(id);
        if (messages != null) {
            messages.forEach(messageRepository::delete);
        }

        List<ReadStatus> readStatuses = readStatusRepository.findAllByChannelId(id);
        if (readStatuses != null) {
            readStatuses.stream().
                    map(ReadStatus::getId).
                    forEach(readStatusRepository::deleteById);
        }

        channelRepository.delete(channel);
        log.info("채널 삭제 완료: " + id);
    }

    public void clear() {
        channelRepository.clear();
    }

    // 메시지가 없을 수 있으므로 반환타입을 Optional로 감쌈
    public Optional<Instant> latestMessageAddedAt(UUID id) {
        Channel channel = channelRepository.findById(id);
        if (channel == null) {
            throw new NotFoundException("존재하지 않는 채널입니다.");
        }

        List<UUID> messageIds = channel.getMessages();
        if (messageIds == null || messageIds.isEmpty()) {
            return Optional.empty();
        }

        List<Message> messages = messageRepository.findAllByIdIn(channel.getMessages());

        return messages.stream()
                .map(Message::getCreatedAt)
                .max(Instant::compareTo);
    }

    // TODO 추후 개선 필요
    // 수정/삭제 시 유저 검증
//    public void validateCreator(User user, Channel channel){
//        if (!channel.getUserId().equals(user.getId())) {
//            throw new IllegalStateException("채널 수정 또는 삭제는 생성한 사람만 가능합니다.");
//        }
//    }

    // 채널 참여중인 사람인지 검증
//    public void validateParticipant(User user, Channel channel) {
//        if (!channel.getParticipants().contains(user)) {
//            throw new IllegalStateException("채널에 참여하지 않은 유저입니다.");
//        }
//    }
}
