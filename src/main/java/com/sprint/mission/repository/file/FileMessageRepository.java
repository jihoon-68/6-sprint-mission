package com.sprint.mission.repository.file;

import com.sprint.mission.dto.message.MessageCreateDto;
import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.entity.Message;
import com.sprint.mission.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileMessageRepository extends SaveAndLoadCommon<Message> implements MessageRepository {

    public FileMessageRepository() {
        super(Message.class);
    }

    @Override
    public Message save(MessageCreateDto messageCreateDto) {
        Message message = new Message(
                messageCreateDto.getChannelId(),
                messageCreateDto.getSenderId(),
                messageCreateDto.getMessageContent(),
                messageCreateDto.getAttachments());

        save(message);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        if(load(id).isEmpty()) return Optional.empty();
        return load(id);
    }

    @Override
    public List<Message> findAll() {
        return loadAll();
    }

    @Override
    public boolean existsById(UUID id) {
        return load(id).isPresent();
    }

    @Override
    public void deleteById(UUID id) {
        delete(id);
    }

    @Override
    public void deleteByChannelId(UUID channelId) {
        List<Message> messages = loadAll().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
        messages.forEach(message -> delete(message.getId()));
    }
}
