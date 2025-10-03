package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelUpdateRequestDto;
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
        Channel channel = new Channel(ChannelType.PRIVATE, "", "");

        // ReadStatus 생성
        if (!dto.participantIds().isEmpty()) {
            dto.participantIds()
                    .forEach(userId ->
                            readStatusRepository.save(new ReadStatus(userId, channel.getId())));
        }

        channelRepository.save(channel);
        log.info("채널 추가 완료: " + channel.getName());
        return ChannelResponseDto.privateChannel( // private 채널은 name, description이 없음.
                channel.getId(),
                channel.getParticipants()
        );
    }

    public ChannelResponseDto createPublicChannel(PublicChannelCreateRequestDto dto) {
        Channel channel = new Channel(ChannelType.PUBLIC, dto.name(), dto.description());

        channel.setName(dto.name());

        if (dto.description() != null) {
            channel.setDescription(dto.description());
        }

        channelRepository.save(channel);
        log.info("채널 추가 완료: " + channel.getName());
        return ChannelResponseDto.publicChannel( // public 채널은 participants가 없음.
                channel.getId(),
                channel.getName(),
                channel.getDescription()
        );
    }

    public ChannelResponseDto findById(UUID id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다."));

        if (channel.getType() == ChannelType.PRIVATE) {
            return ChannelResponseDto.privateChannel(
                    channel.getId(),
                    channel.getParticipants());
        }
        else return ChannelResponseDto.publicChannel(
                channel.getId(),
                channel.getName(),
                channel.getDescription()
                );
    }

    // 전체 PUBLIC, 참여중인 PRIVATE 채널
    public List<ChannelResponseDto> findAllByUserId(UUID id) {
        List<UUID> privateChannelIds = readStatusRepository.findAllByUserId(id)
                .stream()
                .map(ReadStatus::getChannelId)
                .toList();

        return channelRepository.findAll().stream()
                .filter(channel ->
                        channel.getType().equals(ChannelType.PUBLIC) || // 공개 채널
                                privateChannelIds.contains(channel.getId()) // 비공개 채널
                )
                .map(channel -> {
                    if (channel.getType() == ChannelType.PRIVATE) {
                        return ChannelResponseDto.privateChannel(
                                channel.getId(),
                                channel.getParticipants()
                        );
                    } else {
                        return ChannelResponseDto.publicChannel(
                                channel.getId(),
                                channel.getName(),
                                channel.getDescription()
                        );
                    }
                })
                .toList();
    }

    public ChannelResponseDto update(UUID id, PublicChannelUpdateRequestDto dto) {
        // validateCreator(user, channel);
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다."));

        if (channel.getType() == ChannelType.PRIVATE) {
            throw new IllegalStateException("PRIVATE 채널은 수정할 수 없습니다.");
        }

        if (dto.newName() != null) {
            channel.setName(dto.newName());
        };

        if (dto.newDescription() != null) {
            channel.setDescription(dto.newDescription());
        }

        channelRepository.save(channel);
        log.info("수정 및 저장 완료");

        if (channel.getType() == ChannelType.PRIVATE) {
            return new ChannelResponseDto(
                    channel.getId(),
                    ChannelType.PRIVATE,
                    null,
                    null
//                    channel.getParticipants(),
//                    lastMessageAt(id)
            );
        }
        else {
            return new ChannelResponseDto(
                    channel.getId(),
                    ChannelType.PUBLIC,
                    channel.getName(),
                    channel.getDescription()
                    // null,
                    // lastMessageAt(id)
            );
        }
    }

    // 채널 삭제
    public void deleteById(UUID id) {
        // validateCreator(user, channel); // 만든사람만 삭제 가능

        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다."));

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

    public Instant lastMessageAt(UUID id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다."));

        List<UUID> messageIds = channel.getMessages();
        if (messageIds == null || messageIds.isEmpty()) {
            return null;
        }

        List<Message> messages = messageRepository.findAllByIdIn(channel.getMessages());

        return messages.stream()
                .map(Message::getCreatedAt)
                .max(Instant::compareTo)
                .orElse(null);
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
