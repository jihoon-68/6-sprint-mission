package com.sprint.mission.service.basic;

import com.sprint.mission.dto.binarycontent.BinaryContentCreateDto;
import com.sprint.mission.dto.message.MessageCreateDto;
import com.sprint.mission.dto.message.MessageUpdateDto;
import com.sprint.mission.entity.BinaryContent;
import com.sprint.mission.entity.Message;
import com.sprint.mission.repository.BinaryContentRepository;
import com.sprint.mission.repository.ChannelRepository;
import com.sprint.mission.repository.MessageRepository;
import com.sprint.mission.repository.UserRepository;
import com.sprint.mission.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;


    @Override
    public Message create(MessageCreateDto messageCreateDto) {
        Message message = messageRepository.save(messageCreateDto);
        for (BinaryContent attachment : messageCreateDto.getAttachments()) {
            binaryContentRepository.save(new BinaryContentCreateDto(message.getId()));
        }
        return message;
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        return messageRepository.findAll().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public void update(MessageUpdateDto messageUpdateDto) {

    }

    @Override
    public void delete(UUID id) {
        messageRepository.deleteById(id);
    }
}
