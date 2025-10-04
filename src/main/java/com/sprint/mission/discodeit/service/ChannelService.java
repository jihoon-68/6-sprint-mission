package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelUpdateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.*;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    // 채널 생성 및 저장
    public ChannelResponseDto createPrivateChannel(PrivateChannelCreateRequestDto dto) {

        Channel channel = Channel.builder()
                .type(ChannelType.PRIVATE)
                .build();
        channelRepository.save(channel);

        List<User> users = userRepository.findAllById(dto.participantIds());
        List<UserStatus> userStatuses = userStatusRepository.findAllById(dto.participantIds());

        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        List<ReadStatus> readStatuses = new ArrayList<>();

        Map<UUID, UserStatus> statusMap = userStatuses.stream()
                .collect(Collectors.toMap(status -> status.getUser().getId(), status -> status));

        users.forEach(user -> {
            boolean isOnline = Optional.ofNullable(statusMap.get(user.getId()))
                    .map(UserStatus::isOnline)
                    .orElse(false);
            UserMapper.toDto(user, isOnline);

            readStatuses.add(ReadStatus.builder()
                    .user(user)
                    .channel(channel)
                    .lastReadAt(Instant.now())
                    .build());
        });

        readStatusRepository.saveAll(readStatuses);

        log.info("채널 추가 완료: " + channel.getName());
        return ChannelResponseDto.privateChannel( // private 채널은 name, description이 없음.
                channel.getId(),
                userResponseDtos
        );
    }

    public ChannelResponseDto createPublicChannel(PublicChannelCreateRequestDto dto) {
        Channel channel = Channel.builder()
                .type(ChannelType.PUBLIC)
                .name(dto.name())
                .description(dto.description())
                .build();

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
                    getUserResponseDtos(channel.getId(), readStatusRepository, userStatusRepository)
                    );
        }

        else return ChannelResponseDto.publicChannel(
                channel.getId(),
                channel.getName(),
                channel.getDescription()
                );
    }

    // 전체 PUBLIC, 참여중인 PRIVATE 채널
    public List<ChannelResponseDto> findAllByUserId(UUID id) {

        List<ReadStatus> readStatuses = readStatusRepository.findAllByUserId(id);

        List<UUID> privateChannelIds = readStatuses.stream()
                .map(readStatus -> readStatus.getChannel().getId())
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
                                getUserResponseDtos(channel.getId(), readStatusRepository, userStatusRepository)
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
            return ChannelResponseDto.privateChannel(
                    channel.getId(),
                    getUserResponseDtos(channel.getId(), readStatusRepository, userStatusRepository)
            );
        }
        else {
            return ChannelResponseDto.publicChannel(
                    channel.getId(),
                    channel.getName(),
                    channel.getDescription()
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
            messageRepository.deleteAll(messages);
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

        List<UUID> messageIds = channel.getMessages().stream()
                .map(Message::getId).toList();

        if (messageIds.isEmpty()) {
            return null;
        }

        List<Message> messages = messageRepository.findAllByIdIn(
                channel.getMessages().stream().map(Message::getId).toList()
        );

        return messages.stream()
                .map(Message::getCreatedAt)
                .max(Instant::compareTo)
                .orElse(null);
    }

    public static List<UserResponseDto> getUserResponseDtos(UUID channelId,
                                                            ReadStatusRepository readStatusRepository,
                                                            UserStatusRepository userStatusRepository) {
        // ReadStatus 가져오기 (다대다 연결테이블)
        List<ReadStatus> readStatuses = readStatusRepository.findAllByChannelId(channelId);

        // User, UserId 가져오기
        List<User> users = readStatuses.stream()
                .map(ReadStatus::getUser)
                .toList();
        List<UUID> userIds = readStatuses.stream()
                .map(readStatus -> readStatus.getUser().getId())
                .collect(Collectors.toList());

        // UserStatus 가져오기
        List<UserStatus> userStatuses = userStatusRepository.findAllById(userIds);

        Map<UUID, UserStatus> statusMap = userStatuses.stream()
                .collect(Collectors.toMap(status -> status.getUser().getId(), status -> status));

        List<UserResponseDto> userResponseDtos;
        return userResponseDtos = users.stream()
                .map(user -> {
                            boolean isOnline = Optional.ofNullable(statusMap.get(user.getId()))
                                    .map(UserStatus::isOnline)
                                    .orElse(false);
                            return  UserMapper.toDto(user, isOnline);
                        }
                )
                .toList();
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
