package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContent.FileDTO;
import com.sprint.mission.discodeit.dto.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.utilit.FileUpload;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final FileUpload fileUpload;

    @Override
    public Message create(List<MultipartFile> multipartFiles, CreateMessageDTO createMessageDTO) {
        if(!userRepository.existsById(createMessageDTO.authorId())
                || !channelRepository.existsById(createMessageDTO.channelId())){

            throw new NoSuchElementException("Sender Or Receiver channels are mandatory");
        }


        if(!multipartFiles.isEmpty()){
            List<FileDTO> files = fileUpload.upload(multipartFiles);

            if(files.isEmpty()){
                throw new NullPointerException("Multipart files is empty");
            }

            List<UUID> attachmentIds = files.stream()
                            .map(path -> binaryContentRepository.save(
                                    new BinaryContent(
                                            path.FileName(),
                                            path.file().getFreeSpace(),
                                            path.file().getClass().getTypeName(),
                                            path.file().toString().getBytes(StandardCharsets.UTF_8)
                                    ))
                                    .getId()
                            ).toList();


            return messageRepository.save(new Message(
                    createMessageDTO.authorId(),
                    createMessageDTO.channelId(),
                    createMessageDTO.content(),
                    attachmentIds
            ));
        }

        return messageRepository.save(new Message(
                createMessageDTO.authorId(),
                createMessageDTO.channelId(),
                createMessageDTO.content()
        ));
    }

    @Override
    public Message find(UUID id) {
        return messageRepository.findById(id).orElseThrow(() -> new  NoSuchElementException("Message not found"));
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        List<Message> messages = messageRepository.findAll();

        return messages.stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public Message update(UUID messageId, String newContent) {
        Message messageUpdated = messageRepository.findById(messageId)
                .orElseThrow(() -> new  NoSuchElementException("Message not found"));

        messageUpdated.update(newContent);
        return messageRepository.save(messageUpdated);
    }

    @Override
    public void delete(UUID id) {
        Message message = messageRepository
                .findById(id).orElseThrow(() -> new  NoSuchElementException("Message not found"));

        message.getAttachmentIds().forEach(binaryContentRepository::deleteById);
        messageRepository.deleteById(id);
    }
}
