package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.BinaryContent.CreateBinaryContentDTO;
import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;



    @Override
    public Message create(CreateMessageDTO createMessageDTO) {
        boolean sender = userRepository.existsById(createMessageDTO.userId());
        boolean ReceiverChannel= channelRepository.existsById(createMessageDTO.channelId());
        if(!sender || !ReceiverChannel){
            throw new NullPointerException("Sender Or Receiver channels are mandatory");
        }
        createMessageDTO.attachments()
                .forEach(attachment-> binaryContentRepository.save(
                        new BinaryContent(
                                new CreateBinaryContentDTO(createMessageDTO.userId(),
                                        createMessageDTO.channelId()
                                        ,attachment
                                )
                        )
                ));

        return messageRepository.save(new Message(createMessageDTO.userId(),
                createMessageDTO.channelId(), createMessageDTO.content()));
    }

    @Override
    public Message find(UUID id) {
        return messageRepository.findById(id).orElseThrow(() -> new  NullPointerException("Message not found"));
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        return messageRepository.findAll().stream()
                .filter(message -> message.getId().equals(channelId))
                .toList();
    }

    @Override
    public void update(UpdateMessageDTO updateMessageDTO) {
        Message messageUpdated = messageRepository.findById(updateMessageDTO.id())
                .orElseThrow(() -> new  NullPointerException("Message not found"));
        messageUpdated.update(updateMessageDTO.Content());
        messageRepository.save(messageUpdated);
    }

    @Override
    public void delete(UUID id) {
        binaryContentRepository.findAll().stream()
                .filter(binaryContent -> binaryContent.getId().equals(id))
                .forEach(binaryContentDelete ->
                        binaryContentRepository.deleteById(binaryContentDelete.getId()));
        messageRepository.deleteById(id);
    }
}
