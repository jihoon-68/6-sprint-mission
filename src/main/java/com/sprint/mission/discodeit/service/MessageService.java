package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.PageResponse;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.enums.BinaryContentType;
import com.sprint.mission.discodeit.exception.channel.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.message.MessageListNotFoundException;
import com.sprint.mission.discodeit.exception.message.MessageNotFoundException;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final MessageMapper messageMapper; // 유저 온라인 여부 확인시 리포지토리 필요, 스태틱으로 사용 불가해 별도로 선언.

    // 메시지 생성
    @Transactional
    public MessageResponseDto create(/* UUID userId,*/
                                     MessageCreateRequestDto dto,
                                     List<BinaryContentCreateRequestDto> binaryContentCreateRequests) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException(dto.userId()));
        Channel channel = channelRepository.findById(dto.channelId())
                .orElseThrow(() -> new ChannelNotFoundException(dto.channelId()));

        Message message = Message.builder()
                .author(user)
                .channel(channel)
                .content(dto.content())
                // .attachments(dto.binaryContents())
                .build();

        binaryContentCreateRequests
                .forEach(request -> {
                    byte[] bytes = request.bytes();
                    BinaryContent binaryContent = BinaryContent.builder()
                                    .user(user)
                                    .message(message)
                                    .fileName(request.fileName())
                                    .extension(request.extension())
                                    .type(BinaryContentType.ATTACH_IMAGE)
                                    // .data(bytes)
                                    .size((long) bytes.length)
                                    .build();
                    binaryContentRepository.save(binaryContent);
                });

        user.getMessages().add(message);
        channel.getMessages().add(message);
        messageRepository.save(message);

        log.info("메시지 생성이 완료되었습니다. id=" + message.getId());
        return messageMapper.toDto(message);
    }

    @Transactional(readOnly = true)
    public MessageResponseDto findById(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));

        return messageMapper.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageResponseDto> findByChannelId(UUID channelId) {

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));

        List<Message> messages = messageRepository.findByChannelId(channelId);

        if (messages.isEmpty()) {
            throw new MessageListNotFoundException(channelId);
        }

//        // 해당채널에 메시지 남긴 모든 유저ID 조회
//        Set<UUID> userIds = messages.stream()
//                .map(message -> message.getUser().getId())
//                .collect(Collectors.toSet());
//
//        // UserStatus 조회
//        List<UserStatus> userStatuses = userStatusRepository.findAllById(userIds);
//
//        // Map으로 UserStatus 빠른 조회
//        Map<UUID, UserStatus> statusMap = userStatuses.stream()
//                .collect(Collectors.toMap(status -> status.getUser().getId(), status -> status));

        return messages.stream()
                .map(messageMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PageResponse<MessageResponseDto> findByChannelId(UUID channelId, Instant cursor, int size) {

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));

        Pageable pageable = PageRequest.of(
                0, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Message> messages = messageRepository.findByChannelIdAndCursor(channelId, cursor, pageable);


        if (messages.isEmpty()) {
            throw new MessageListNotFoundException(channelId);
        }

        Instant nextCursor = messages.size() < size ? null : messages.get(messages.size() - 1).getCreatedAt();
        List<MessageResponseDto> dtoList = messages.stream()
                .map(messageMapper::toDto)
                .toList();
        return new PageResponse<>(
                dtoList,
                nextCursor,
                size,
                nextCursor != null, // hasNext 여부
                null
        ); // totalElements는 커서 방식에서는 비효율적이라 보통 null 처리;
    }

    // 내용 수정
    @Transactional
    public MessageResponseDto update(UUID id, MessageUpdateRequestDto dto) {
        // validateWriter(user, message);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));

        message.setContent(dto.newContent());
        messageRepository.save(message);
        log.info("메시지 수정이 완료되었습니다. id=" + message.getId());

        return messageMapper.toDto(message);
    }

    // 삭제
    @Transactional
    public void delete(UUID id) {
        // validateWriter(user, message);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));

        List<UUID> binaryContentIds = message.getAttachments().stream().map(BinaryContent::getId).toList();
        if (message.getAttachments() != null) {
            for (UUID binaryContentId : binaryContentIds) {
                binaryContentRepository.deleteById(id);
            }
        }

        messageRepository.delete(message);
        log.info("메시지 삭제가 완료되었습니다. id=" + id);
    }
}
