package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.BinaryContent.CreateBinaryContentDTO;
import com.sprint.mission.discodeit.DTO.BinaryContent.FileDTO;
import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
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

import java.nio.file.Path;
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
        if(!userRepository.existsById(createMessageDTO.userId())
                || !channelRepository.existsById(createMessageDTO.channelId())){

            throw new NoSuchElementException("Sender Or Receiver channels are mandatory");
        }


        if(!multipartFiles.isEmpty()){
            List<FileDTO> files = fileUpload.upload(multipartFiles);
            if(multipartFiles.isEmpty()){
                throw new NullPointerException("Multipart files is empty");
            }

            List<Path> filePaths = files.stream()
                    .map(file -> file.file().toPath())
                    .toList();

            List<UUID> attachmentIds = filePaths.stream()
                            .map(path -> binaryContentRepository.save(
                                    new BinaryContent(
                                            createMessageDTO.userId(),
                                            createMessageDTO.channelId(),
                                            path.toString()
                                    )).getId()
                            ).toList();


            return messageRepository.save(new Message(
                    createMessageDTO.userId(),
                    createMessageDTO.channelId(),
                    createMessageDTO.content(),
                    attachmentIds
            ));
        }

        return messageRepository.save(new Message(
                createMessageDTO.userId(),
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
        return messageRepository.findAll().stream()
                .filter(message -> message.getChannel().equals(channelId))
                .toList();
    }

    @Override
    public void update(List<MultipartFile> multipartFiles, UpdateMessageDTO updateMessageDTO) {
        Message messageUpdated = messageRepository.findById(updateMessageDTO.id())
                .orElseThrow(() -> new  NoSuchElementException("Message not found"));
        if(!multipartFiles.isEmpty()){
            List<FileDTO> files = fileUpload.upload(multipartFiles);
            List<UUID> attachmentIds = files.stream()
                    .map(file -> binaryContentRepository.save(
                            new BinaryContent(
                                    messageUpdated.getSender(),
                                    messageUpdated.getChannel(),
                                    file.file().toString()
                            )
                    ).getId())
                    .toList();
            messageUpdated.update(updateMessageDTO.Content(), attachmentIds);
        }else{
            messageUpdated.update(updateMessageDTO.Content(), null);
        }
        messageRepository.save(messageUpdated);
    }

    @Override
    public void delete(UUID id) {
        Message message = messageRepository
                .findById(id).orElseThrow(() -> new  NoSuchElementException("Message not found"));

        message.getAttachmentIds().forEach(binaryContentRepository::deleteById);
        messageRepository.deleteById(id);
    }
}
