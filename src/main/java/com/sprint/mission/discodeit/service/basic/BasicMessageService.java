package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.BinaryContentDto;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.MessageResponse;
import com.sprint.mission.discodeit.dto.response.PagedResponse;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest.CreateMessageWithContent;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentStorage binaryContentStorage;

    @Override
    @Transactional
    public MessageResponse create(CreateMessageWithContent createRequest) {
        UUID channelId = createRequest.channelId();
        UUID authorId = createRequest.authorId();

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException("Channel with id " + channelId + " does not exist"));
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + authorId + " does not exist"));

        List<BinaryContent> attachments = new ArrayList<>();
        BinaryContentDto attachmentDto = createRequest.binaryContent();

        if (attachmentDto != null) {
            UUID contentId = UUID.randomUUID();

            binaryContentStorage.put(contentId, attachmentDto.data());

            BinaryContent binaryContent = new BinaryContent(
                    attachmentDto.filename(),
                    attachmentDto.size(),
                    attachmentDto.contentType()
            );
            attachments.add(binaryContent);
        }

        String content = createRequest.content();

        Message message = new Message(
                content,
                channel,
                author,
                attachments
        );
        Message savedMessage = messageRepository.save(message);

        return MessageResponse.from(savedMessage);
    }

    @Override
    public MessageResponse find(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Message with id " + messageId + " not found"));
        return MessageResponse.from(message);
    }

    @Override
    public PagedResponse<MessageResponse> findAllByChannelId(UUID channelId, int page) {

        int requestSize = 50;
        int pageSizeForQuery = requestSize + 1;
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, pageSizeForQuery, sort);
        List<Message> messagesWithExtra = messageRepository.findAllByChannelId(channelId, pageable);
        boolean hasNext = messagesWithExtra.size() > requestSize;
        List<Message> contentMessages = hasNext
                ? messagesWithExtra.subList(0, requestSize)
                : messagesWithExtra;

        List<MessageResponse> responseList = contentMessages.stream()
                .map(MessageResponse::from)
                .collect(Collectors.toList());

        return PagedResponse.of(responseList, page, requestSize, hasNext);
    }

    @Override
    @Transactional
    public MessageResponse update(UUID messageId, MessageUpdateRequest request) {
        String newContent = request.newContent();
        Message message = messageRepository.findById(messageId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Message with id " + messageId + " not found"));

        message.update(newContent);
        return MessageResponse.from(message);
    }

    @Override
    @Transactional
    public void delete(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Message with id " + messageId + " not found"));

        message.getAttachments().forEach(attachment -> {
            binaryContentRepository.delete(attachment);
        });

        messageRepository.delete(message);
    }
}