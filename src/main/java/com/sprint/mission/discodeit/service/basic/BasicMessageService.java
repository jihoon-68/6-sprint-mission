package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.base.BaseEntity;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  //
  private final ChannelRepository channelRepository;
  private final UserRepository userRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentStorage storage;

  @Override
  public Message create(CreateMessageRequest createMessageRequest,
      List<MultipartFile> attachments) {
    Channel channel = channelRepository.findById(createMessageRequest.channelId())
        .orElseThrow(() -> new NotFoundException(
            "채널이 없습니다 " + createMessageRequest.channelId()));
    User author = userRepository.findById(createMessageRequest.authorId())
        .orElseThrow(() -> new NotFoundException(
            "해당 유저가 없습니다 " + createMessageRequest.authorId()));

    List<MultipartFile> attachmentsNotNull =
        attachments != null ? attachments : Collections.emptyList();

    List<BinaryContent> binaryContents = attachmentsNotNull.stream().map(
        file -> {
          try {
            BinaryContent binaryContent = new BinaryContent(
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType()
            );
            storage.put(binaryContent.getId(), file.getBytes());
            return binaryContentRepository.save(binaryContent);
          } catch (IOException e) {
            log.error("첨부파일 처리 실패", e);
            throw new RuntimeException("첨부파일 처리 실패");
          }
        }
    ).toList();

    Message message = new Message(createMessageRequest.content(), channel, author, binaryContents);
    return messageRepository.save(message);
  }

  @Override
  @Transactional(readOnly = true)
  public Message find(UUID messageId) {
    return messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NotFoundException("Message with id " + messageId + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Message> findAllByChannelId(UUID channelId) {
    return messageRepository.findAllByChannel_Id(channelId);
  }

  @Override
  public Message update(UUID messageId, UpdateMessageRequest updateMessageRequest) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NotFoundException("Message with id " + messageId + " not found"));
    message.update(updateMessageRequest.newContent());
    return messageRepository.save(message);
  }

  @Override
  public void delete(UUID messageId) {
    if (!messageRepository.existsById(messageId)) {
      throw new NotFoundException("Message with id " + messageId + " not found");
    }
    // 메시지의 첨부파일들 객체 삭제
    Message message = messageRepository.findById(messageId).orElse(null);
    List<UUID> binaryContentIds = message.getAttachments()
        .stream()
        .map(BaseEntity::getId)
        .toList();
    binaryContentRepository.deleteAllById(binaryContentIds);
    // 메시지 id로 삭제
    messageRepository.deleteById(messageId);
  }
}
