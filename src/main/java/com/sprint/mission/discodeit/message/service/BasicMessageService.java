package com.sprint.mission.discodeit.message.service;

import com.sprint.mission.discodeit.binarycontent.BinaryContentDto;
import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent.OwnerType;
import com.sprint.mission.discodeit.binarycontent.service.BinaryContentService;
import com.sprint.mission.discodeit.message.MessageDto.Request;
import com.sprint.mission.discodeit.message.MessageDto.Response;
import com.sprint.mission.discodeit.message.MessageMapper;
import com.sprint.mission.discodeit.message.domain.Message;
import com.sprint.mission.discodeit.message.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BasicMessageService implements MessageService {

    private final BinaryContentService binaryContentService;
    private final MessageRepository messageRepository;

    public BasicMessageService(
            BinaryContentService binaryContentService,
            MessageRepository messageRepository
    ) {
        this.binaryContentService = binaryContentService;
        this.messageRepository = messageRepository;
    }

    @Override
    public Response createMessage(Request request) {
        Message message = MessageMapper.from(request);
        message = messageRepository.save(message);
        for (String messageAttachmentBase64 : request.messageAttachmentsBase64()) {
            var messageAttachmentRequest = new BinaryContentDto.Request(
                    OwnerType.MESSAGE_ATTACHMENT,
                    message.getId(),
                    messageAttachmentBase64
            );
            binaryContentService.createBinaryContent(messageAttachmentRequest);
        }
        return MessageMapper.toResponse(message);
    }

    @Override
    public Set<Response> getMessagesByChannelId(UUID channelId) {
        Iterable<Message> messages = messageRepository.findAllByChannelId(channelId);
        return StreamSupport.stream(messages.spliterator(), false)
                .map(MessageMapper::toResponse)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Response updateMessageById(UUID messageId, Request request) {
        Message message = messageRepository.findById(messageId);
        binaryContentService.getMessageAttachmentsByMessageId(message.getId())
                        .forEach(response -> binaryContentService.deleteBinaryContentById(response.id()));
        message = message.with(request.content());
        message = messageRepository.save(message);
        return MessageMapper.toResponse(message);
    }

    @Override
    public void deleteMessageById(UUID messageId) {
        Message message = messageRepository.findById(messageId);
        binaryContentService.getMessageAttachmentsByMessageId(message.getId())
                        .forEach(response -> binaryContentService.deleteBinaryContentById(response.id()));
        messageRepository.deleteById(message.getId());
    }
}
