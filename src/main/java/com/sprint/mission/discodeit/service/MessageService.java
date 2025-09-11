package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
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

        if (dto.binaryContents() != null) {
            message.setBinaryContents(dto.binaryContents());
        }

        messageRepository.save(message);
        userRepository.findById(dto.userId()).getCreatedMessages().add(message.getId());
        channelRepository.findById(dto.channelId()).getMessages().add(message.getId());

        System.out.println("메시지 추가 완료: " + message.getContent());
        return new MessageResponseDto(
                message.getId(),
                message.getUserId(),
                message.getChannelId(),
                message.getContent(),
                message.getBinaryContents()
        );
    }

    public MessageResponseDto findById(UUID id) {
        Message message = messageRepository.findById(id);
        if (message == null) {
            throw new NoSuchElementException("존재하지 않는 메시지입니다.");
        }
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
            throw new NoSuchElementException("채널이 존재하지 않거나, 메세지가 하나도 없습니다.");
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
    public MessageResponseDto update(MessageUpdateRequestDto dto) {
        // validateWriter(user, message);
        Message message = messageRepository.findById(dto.messageId());
        if (message == null) {
            throw new NoSuchElementException("존재하지 않는 메시지입니다.");
        }

        message.setContent(dto.content());
        messageRepository.save(message);
        System.out.println("내용 수정 완료 : " + dto.content());
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
        Message message = messageRepository.findById(id);
        if (message == null) {
            throw new NoSuchElementException("존재하지 않는 메시지입니다.");
        }

        List<BinaryContent> binaryContents = message.getBinaryContents();
        if (message.getBinaryContents() != null) {
            for (BinaryContent content : binaryContents) {
                binaryContentRepository.deleteById(content.getId());
            }
        }

        messageRepository.delete(message);
        System.out.println("메시지 삭제 완료: " + id);
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
