package com.sprint.mission.repository.file;

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
    public Message save(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteByChannelId(UUID channelId) {

    }
}
