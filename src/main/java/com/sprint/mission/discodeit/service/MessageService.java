package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.PageResponse;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
    private final MessageMapper messageMapper; // 유저 온라인 여부 확인시 리포지토리 필요, 스태틱으로 사용 불가해 별도로 선언.

    // 메시지 생성
    @Transactional
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
        messageRepository.save(message); // 명시적 저장

        log.info("메시지 추가 완료: " + message.getContent());
        return messageMapper.toDto(message);
    }

    @Transactional(readOnly = true)
    public MessageResponseDto findById(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메시지입니다."));

        return messageMapper.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageResponseDto> findByChannelId(UUID id) {
        List<Message> messages= messageRepository.findByChannelId(id);
        if (messages.isEmpty()) {
            throw new NotFoundException("채널이 존재하지 않거나, 메세지가 하나도 없습니다.");
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

    public PageResponse<MessageResponseDto> findByChannelId(UUID channelId, Instant cursor, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Message> messages = messageRepository.findByChannelIdAndCursor(channelId, cursor, pageable);

        if (messages.isEmpty()) {
            throw new NotFoundException("채널이 존재하지 않거나, 메세지가 하나도 없습니다.");
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
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메시지입니다."));

        message.setContent(dto.newContent());
        messageRepository.save(message);
        log.info("내용 수정 완료 : " + dto.newContent());

        return messageMapper.toDto(message);
    }

    // 삭제
    @Transactional
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

    @Transactional
    public void clear() {
        messageRepository.clear();
    }
}
