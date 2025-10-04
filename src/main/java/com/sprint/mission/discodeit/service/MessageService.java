package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final UserStatusRepository userStatusRepository;

    // 메시지 생성
    public MessageResponseDto create(MessageCreateRequestDto dto,
                                     List<BinaryContentCreateRequestDto> binaryContentCreateRequests) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다: " + dto.userId()));
        Channel channel = channelRepository.findById(dto.channelId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다: " + dto.channelId()));

        Message message = Message.builder()
                .user(user)
                .channel(channel)
                .content(dto.content())
                .binaryContents(dto.binaryContents())
                .build();

        List<UUID> attachmentIds = binaryContentCreateRequests.stream()
                .map(attachmentRequest -> {

                    byte[] bytes = attachmentRequest.bytes();

                    BinaryContent binaryContent = BinaryContent.builder()
                            .user(user)
                            .message(message)
                            .fileName(attachmentRequest.fileName())
                            .extension(attachmentRequest.extension())
                            .type(BinaryContentType.ATTACH_IMAGE)
                            .data(bytes)
                            .size((long) bytes.length)
                            .build();
                    binaryContentRepository.save(binaryContent);
                    return binaryContent.getId();
                })
                .toList();

        user.getMessages().add(message);
        channel.getMessages().add(message);
        messageRepository.save(message);

        log.info("메시지 추가 완료: " + message.getContent());
        return MessageResponseDto.builder()
                .id(message.getId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .content(message.getContent())
                .channelId(message.getChannel().getId())
                .author(UserMapper.toDto(message.getUser(), getIsUserOnline(message, userStatusRepository)))
                .attachments(getBinaryContentResponseDtos(message))
                .build();
    }

    public MessageResponseDto findById(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메시지입니다."));

        return MessageResponseDto.builder()
                .id(message.getId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .content(message.getContent())
                .channelId(message.getChannel().getId())
                .author(UserMapper.toDto(message.getUser(), getIsUserOnline(message, userStatusRepository)))
                .attachments(getBinaryContentResponseDtos(message))
                .build();
    }

    public List<MessageResponseDto> findByChannelId(UUID id) {
        List<Message> messages= messageRepository.findByChannelId(id);
        if (messages.isEmpty()) {
            throw new NotFoundException("채널이 존재하지 않거나, 메세지가 하나도 없습니다.");
        }

        // 해당채널에 메시지 남긴 모든 유저ID 조회
        Set<UUID> userIds = messages.stream()
                .map(message -> message.getUser().getId())
                .collect(Collectors.toSet());

        // UserStatus 조회
        List<UserStatus> userStatuses = userStatusRepository.findAllById(userIds);

        // Map으로 UserStatus 빠른 조회
        Map<UUID, UserStatus> statusMap = userStatuses.stream()
                .collect(Collectors.toMap(status -> status.getUser().getId(), status -> status));

        return messages.stream()
                .map(message -> {

                    return MessageResponseDto.builder()
                                    .id(message.getId())
                                    .createdAt(message.getCreatedAt())
                                    .updatedAt(message.getUpdatedAt())
                                    .content(message.getContent())
                                    .channelId(message.getChannel().getId())
                                    .author(UserMapper.toDto(message.getUser(), getIsUserOnline(message, userStatusRepository)))
                                    .attachments(getBinaryContentResponseDtos(message))
                                    .build();
                        }
                )
                .toList();
    }

    // 내용 수정
    public MessageResponseDto update(UUID id, MessageUpdateRequestDto dto) {
        // validateWriter(user, message);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메시지입니다."));

        message.setContent(dto.newContent());
        messageRepository.save(message);
        log.info("내용 수정 완료 : " + dto.newContent());

        return MessageResponseDto.builder()
                .id(message.getId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .content(message.getContent())
                .channelId(message.getChannel().getId())
                .author(UserMapper.toDto(message.getUser(), getIsUserOnline(message, userStatusRepository)))
                .attachments(getBinaryContentResponseDtos(message))
                .build();
    }

    // 삭제
    public void delete(UUID id) {
        // validateWriter(user, message);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메시지입니다."));

        List<UUID> binaryContentIds = message.getBinaryContents().stream().map(BinaryContent::getId).toList();
        if (message.getBinaryContents() != null) {
            for (UUID binaryContentId : binaryContentIds) {
                binaryContentRepository.deleteById(id);
            }
        }

        messageRepository.delete(message);
        log.info("메시지 삭제 완료: " + id);
    }

    public void clear() {
        messageRepository.clear();
    }

    public static Boolean getIsUserOnline(Message message, UserStatusRepository userStatusRepository) {
        return userStatusRepository.findByUserId(message.getUser().getId())
                .map(UserStatus::isOnline)
                .orElse(false);
    }

    public static List<BinaryContentResponseDto> getBinaryContentResponseDtos(Message message) {
        return message.getBinaryContents().stream()
                .map(BinaryContentMapper::toDto)
                .toList();
    }
}
