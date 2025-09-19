package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.CreateAttachmentImageDto;
import com.sprint.mission.discodeit.dto.messagedto.CreateMessageDto;
import com.sprint.mission.discodeit.dto.messagedto.UpdateMessageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    //
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public Message create(CreateMessageDto createMessageDto) {
        if (!channelRepository.existsById(createMessageDto.channelId())) {
            throw new NoSuchElementException("채널이 없습니다 " + createMessageDto.channelId());
        }
        if (!userRepository.existsById(createMessageDto.authorId())) {
            throw new NoSuchElementException("해당 유저가 없습니다 " + createMessageDto.authorId());
        }
        Message message = new Message(createMessageDto.content(), createMessageDto.channelId(), createMessageDto.authorId(), createMessageDto.attachmentIds());
        return messageRepository.save(message);
    }

    @Override
    public Message find(UUID messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        return messageRepository.findAllByChannelId(channelId);
    }

    @Override
    public Message update(UUID messageId, UpdateMessageDto updateMessageDto) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
        message.update(updateMessageDto.newContent());
        return messageRepository.save(message);
    }

    @Override
    public void delete(UUID messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new NoSuchElementException("Message with id " + messageId + " not found");
        }
        // 메시지의 첨부파일들 객체 삭제
        Message message = messageRepository.findById(messageId).orElse(null);
        if(message.getAttachmentIds() != null){
            message.getAttachmentIds().forEach(attachmentId -> binaryContentRepository.deleteById(attachmentId));
        }
        // 메시지 id로 삭제
        messageRepository.deleteById(messageId);
    }
}
