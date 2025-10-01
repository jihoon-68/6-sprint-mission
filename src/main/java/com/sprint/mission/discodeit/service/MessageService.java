package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
    public MessageResponseDto create(MessageCreateRequestDto dto,
                                     List<BinaryContentCreateRequestDto> binaryContentCreateRequests) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다: " + dto.userId()));
        Channel channel = channelRepository.findById(dto.channelId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채널입니다: " + dto.channelId()));

        List<UUID> attachmentIds = binaryContentCreateRequests.stream()
                .map(attachmentRequest -> {
                    String fileName = attachmentRequest.fileName();
                    String extension = attachmentRequest.extension();
                    byte[] bytes = attachmentRequest.bytes();

                    BinaryContent binaryContent = new BinaryContent(fileName, extension,
                            BinaryContentType.ATTACH_IMAGE, bytes, (long) bytes.length);
                    binaryContentRepository.save(binaryContent);
                    return binaryContent.getId();
                })
                .toList();

        Message message = new Message(dto.userId(), dto.channelId(), dto.content());

        user.getCreatedMessages().add(message.getId());
        channel.getMessages().add(message.getId());
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

        message.setContent(dto.newContent());
        messageRepository.save(message);
        log.info("내용 수정 완료 : " + dto.newContent());
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

        List<UUID> binaryContentIds = message.getBinaryContents();
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

}
