package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.MESSAGE_NOT_FOUND_BY_ID;

@Service
public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final MessageMapper messageMapper;
    private final BinaryContentMapper binaryContentMapper;

    public BasicMessageService(
            MessageRepository messageRepository,
            BinaryContentRepository binaryContentRepository,
            MessageMapper messageMapper,
            BinaryContentMapper binaryContentMapper
    ) {
        this.messageRepository = messageRepository;
        this.binaryContentRepository = binaryContentRepository;
        this.messageMapper = messageMapper;
        this.binaryContentMapper = binaryContentMapper;
    }

    @Override
    public MessageDto.Response create(MessageDto.Request request) {
        Message message = messageMapper.from(request);
        message = messageRepository.save(message);
        for (byte[] binary : request.binaries()) {
            BinaryContent messageAttachment = binaryContentMapper.ofMessageAttachment(message.getId(), binary);
            binaryContentRepository.save(messageAttachment);
        }
        return messageMapper.toResponse(message);
    }

    @Override
    public Set<MessageDto.Response> readAll(UUID channelId) {
        return messageRepository.findAll(channelId)
                .stream()
                .map(messageMapper::toResponse)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public MessageDto.Response update(UUID id, MessageDto.Request request) {
        Message message = messageRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(MESSAGE_NOT_FOUND_BY_ID.formatted(id)));
        binaryContentRepository.findAllMessageAttachments(message.getId())
                .forEach(messageAttachment -> binaryContentRepository.delete(messageAttachment.getId()));
        message = messageMapper.update(request.content(), message);
        message = messageRepository.save(message);
        return messageMapper.toResponse(message);
    }

    @Override
    public void delete(UUID id) {
        Message message = messageRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(MESSAGE_NOT_FOUND_BY_ID.formatted(id)));
        binaryContentRepository.findAllMessageAttachments(message.getId())
                .forEach(messageAttachment -> binaryContentRepository.delete(messageAttachment.getId()));
        messageRepository.delete(message.getId());
    }
}
