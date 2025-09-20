package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final BinaryContentRepository binaryContentRepository;

    // 메시지 생성
    public MessageResponseDto create(MessageCreateRequestDto dto) {
        // channelService.validateParticipant(creator, channel); // 유저 검증

        Message message = new Message(dto.userId(), dto.channelId(), dto.content());

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        user.getCreatedMessages().add(message.getId());

        Channel channel = channelRepository.findById(dto.channelId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다."));
        channel.getMessages().add(message.getId());

        if (dto.binaryContents() != null) {
            message.setBinaryContents(dto.binaryContents());
        }

        messageRepository.save(message);

        log.info("메시지 추가 완료: " + message.getContent());
        return new MessageResponseDto(
                message.getId(),
                message.getUserId(),
                message.getChannelId(),
                message.getContent(),
                message.getBinaryContents()
        );
    }

    public MessageResponseDto findById(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메시지입니다."));

        return new MessageResponseDto(
                message.getId(),
                message.getUserId(),
                message.getChannelId(),
                message.getContent(),
                message.getBinaryContents()
        );
    }

    public List<MessageResponseDto> findByChannelId(UUID id) {
        List<Message> messages= messageRepository.findByChannelId(id);
        if (messages.isEmpty()) {
            throw new NotFoundException("채널이 존재하지 않거나, 메세지가 하나도 없습니다.");
        }

        return messages.stream()
                .map(message -> new MessageResponseDto(
                        message.getId(),
                        message.getUserId(),
                        message.getChannelId(),
                        message.getContent(),
                        message.getBinaryContents()
                ))
                .toList();
    }

    // 내용 수정
    public MessageResponseDto update(UUID id, MessageUpdateRequestDto dto) {
        // validateWriter(user, message);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메시지입니다."));

        message.setContent(dto.content());
        messageRepository.save(message);
        log.info("내용 수정 완료 : " + dto.content());
        return new MessageResponseDto(
                message.getId(),
                message.getUserId(),
                message.getChannelId(),
                message.getContent(),
                message.getBinaryContents()
        );
    }

    // 삭제
    public void delete(UUID id) {
        // validateWriter(user, message);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메시지입니다."));

        List<BinaryContent> binaryContents = message.getBinaryContents();
        if (message.getBinaryContents() != null) {
            for (BinaryContent content : binaryContents) {
                binaryContentRepository.deleteById(content.getId());
            }
        }

        messageRepository.delete(message);
        log.info("메시지 삭제 완료: " + id);
    }

    public void clear() {
        messageRepository.clear();
    }

    // 작성자 검증
//    public void validateWriter(UUID userId, UUID messageId) {
//        Message message = findById(messageId);
//        if (!message.getUserId().equals(userId)) {
//            throw new RuntimeException("작성자만 수정 또는 삭제할 수 있습니다.");
//        }
//    }
}
