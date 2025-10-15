package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.BinaryContentDto;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  @Transactional
  public Message create(MessageCreateRequest messageCreateRequest,
      List<BinaryContentDto> binaryContentCreateRequests) {
    UUID channelId = messageCreateRequest.channelId();
    UUID authorId = messageCreateRequest.authorId();

    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(() -> new EntityNotFoundException("Channel with id " + channelId + " does not exist"));
    User author = userRepository.findById(authorId)
        .orElseThrow(() -> new EntityNotFoundException("Author with id " + authorId + " does not exist"));

    List<BinaryContent> attachments = binaryContentCreateRequests.stream()
        .map(attachmentRequest -> new BinaryContent(
            attachmentRequest.filename(),
            attachmentRequest.size(),
            attachmentRequest.contentType(),
            attachmentRequest.bytes()
        ))
        .collect(Collectors.toList());

    String content = messageCreateRequest.content();

    Message message = new Message(
        content,
        channel,
        author,
        attachments
    );
    return messageRepository.save(message);
  }

  @Override
  public Message find(UUID messageId) {
    return messageRepository.findById(messageId)
        .orElseThrow(
            () -> new EntityNotFoundException("Message with id " + messageId + " not found"));
  }

  @Override
  public List<Message> findAllByChannelId(UUID channelId) {
    return messageRepository.findAllByChannelId(channelId);
  }

  @Override
  @Transactional
  public Message update(UUID messageId, MessageUpdateRequest request) {
    String newContent = request.newContent();

    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new EntityNotFoundException("Message with id " + messageId + " not found"));

    message.update(newContent);

    return message;
  }

  @Override
  @Transactional
  public void delete(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new EntityNotFoundException("Message with id " + messageId + " not found"));
    message.getAttachments()
        .forEach(binaryContentRepository::delete);
    messageRepository.delete(message);
  }
}