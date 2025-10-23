package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelUpdateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.*;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ChannelMapper channelMapper;
    private final UserMapper userMapper;
    private final BinaryContentMapper binaryContentMapper;

    // 채널 생성 및 저장
    @Transactional
    public ChannelResponseDto createPrivateChannel(PrivateChannelCreateRequestDto dto) {

        Channel channel = Channel.builder()
                .type(ChannelType.PRIVATE)
                .build();
        channelRepository.save(channel);

        List<User> users = userRepository.findAllById(dto.participantIds());
        List<UserStatus> userStatuses = userStatusRepository.findAllById(dto.participantIds());
        Map<UUID, UserStatus> statusMap = userStatuses.stream()
                .collect(Collectors.toMap(userStatus -> userStatus.getUser().getId(), userStatus -> userStatus));

        List<UserResponseDto> userResponseDtos = users.stream()
                .map(user -> {
                    BinaryContentResponseDto profileImage = binaryContentMapper.toDto(user.getProfile());
                    return userMapper.toDto(user, statusMap.get(user.getId()), profileImage);
                })
                .toList();
        List<ReadStatus> readStatuses = users.stream()
                .map(user -> ReadStatus.builder()
                        .user(user)
                        .channel(channel)
                        .lastReadAt(Instant.now())
                        .build())
                .toList();
        readStatusRepository.saveAll(readStatuses);

        log.info("채널 추가 완료: " + channel.getName());
        return ChannelResponseDto.privateChannel( // private 채널은 name, description이 없음.
                channel.getId(),
                userResponseDtos,
                null
        );
    }

    @Transactional
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
                channel.getDescription(),
                null
        );
    }

    @Transactional(readOnly = true)
    public ChannelResponseDto findById(UUID id) {
        Channel channel = channelRepository.findByIdWithUsers(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다."));

        Instant lastMessageAt = lastMessageAt(channel.getId());

        if (channel.getType() == ChannelType.PRIVATE) {
            return ChannelResponseDto.privateChannel(
                    channel.getId(),
                    getUserResponseDtos(channel),
                    lastMessageAt
                    );
        }

        else return ChannelResponseDto.publicChannel(
                channel.getId(),
                channel.getName(),
                channel.getDescription(),
                lastMessageAt
                );
    }

    // 전체 PUBLIC, 참여중인 PRIVATE 채널
    @Transactional(readOnly = true)
    public List<ChannelResponseDto> findAllByUserId(UUID id) {

        List<ReadStatus> readStatuses = readStatusRepository.findAllByUserId(id);
        Set<UUID> privateChannelIds = readStatuses.stream()
                .map(rs -> rs.getChannel().getId())
                .collect(Collectors.toSet());

        return channelRepository.findAllWithMessagesAndUsers().stream()
                .filter(channel -> channel.getType().equals(ChannelType.PUBLIC) || // 공개 채널
                                privateChannelIds.contains(channel.getId()) // 비공개 채널
                )
                .map(channel -> {
                    if (channel.getType() == ChannelType.PRIVATE) {

                        return ChannelResponseDto.privateChannel(
                                channel.getId(),
                                getUserResponseDtos(channel.getId()),
                                lastMessageAt(channel.getId())
                        );
                    } else {
                        return ChannelResponseDto.publicChannel(
                                channel.getId(),
                                channel.getName(),
                                channel.getDescription(),
                                lastMessageAt(channel.getId())
                        );
                    }
                })
                .toList();
    }

    @Transactional
    public ChannelResponseDto update(UUID id, PublicChannelUpdateRequestDto dto) {
        // validateCreator(user, channel);
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다."));

        if (channel.getType() == ChannelType.PRIVATE) throw new IllegalStateException("PRIVATE 채널은 수정할 수 없습니다.");

        if (dto.newName() != null) channel.setName(dto.newName());
        if (dto.newDescription() != null) channel.setDescription(dto.newDescription());
        channelRepository.save(channel);
        log.info("수정 및 저장 완료");

        if (channel.getType() == ChannelType.PRIVATE) {
            return ChannelResponseDto.privateChannel(
                    channel.getId(),
                    getUserResponseDtos(channel.getId()),
                    lastMessageAt(channel.getId())
            );
        }
        else {
            return ChannelResponseDto.publicChannel(
                    channel.getId(),
                    channel.getName(),
                    channel.getDescription(),
                    lastMessageAt(channel.getId())
            );
        }
    }

    // 채널 삭제
    @Transactional
    public void deleteById(UUID id) {

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

//    @Transactional
//    public void clear() {
//        channelRepository.clear();
//    }

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

//    public ChannelResponseDto getChannelDto(Channel channel, List<UserResponseDto> participants, Instant lastMessageAt) {
//        // MapStruct가 생성한 ChannelMapperImpl 클래스의 toDto 메서드를 사용
//        return channelMapper.toDto(channel, participants, lastMessageAt);
//    }

    // 다건 조회 시 사용
    public List<UserResponseDto> getUserResponseDtos(UUID channelId) {

        List<ReadStatus> readStatuses = readStatusRepository.findAllByChannelIdWithUser(channelId);

            return readStatuses.stream()
                    .map(ReadStatus::getUser)
                    .distinct()
                    .map(user -> {
                        UserStatus userStatus = user.getUserStatus();
                        BinaryContentResponseDto profileImage = binaryContentMapper.toDto(user.getProfile());
                        return userMapper.toDto(user, userStatus, profileImage);
                    })
                    .toList();

        }


//        // ReadStatus 가져오기 (다대다 연결테이블)
//        List<ReadStatus> readStatuses = readStatusRepository.findAllByChannelId(channelId);
//
//        // User, UserId 가져오기
//        List<User> users = readStatuses.stream()
//                .map(ReadStatus::getUser)
//                .toList();
//        List<UUID> userIds = readStatuses.stream()
//                .map(readStatus -> readStatus.getUser().getId())
//                .collect(Collectors.toList());
//
//        // UserStatus 가져오기
//        List<UserStatus> userStatuses = userStatusRepository.findAllById(userIds);
//
//        Map<UUID, UserStatus> statusMap = userStatuses.stream()
//                .collect(Collectors.toMap(status -> status.getUser().getId(), status -> status));
//
//        List<UserResponseDto> userResponseDtos;
//        return userResponseDtos = users.stream()
//                .map(user -> {
//                    UserStatus userStatus = statusMap.get(user.getId());
//                    BinaryContentResponseDto profileImage = binaryContentMapper.toDto(user.getProfileImage());
//                            return userMapper.toDto(user, userStatus,  profileImage);
//                    }
//                )
//                .toList();
//    }

    // 단건 조회 시 사용
    private List<UserResponseDto> getUserResponseDtos(Channel channel) {
        return channel.getReadStatuses().stream()
                .map(ReadStatus::getUser)
                .distinct()
                .map(user -> userMapper.toDto(
                        user,
                        user.getUserStatus(),
                        binaryContentMapper.toDto(user.getProfile())
                ))
                .toList();
    }
}
