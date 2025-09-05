package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public class FileMessageRepository implements MessageRepository {
    private final Path directory = Paths.get("./src/main/resources/MessagesDate");
    private final FileEdit instance = new FileEdit();

    private Path filePaths(UUID id) {return directory.resolve(id + ".ser");}
    public FileMessageRepository() {
        instance.init(directory);
    }

    @Override
    public Message save(Message message) {
        instance.save(filePaths(message.getId()), message);
        return message;
    }
    @Override
    public Optional<Message> findById(UUID id) { return instance.load(directory); }

    @Override
    public List<Message> findAll() {
        return instance.loadAll(directory);
    }

    @Override
    public boolean existsById(UUID id) {
        return findById(id).isPresent();
    }

    @Override
    public void deleteById(UUID id) {
        boolean isDelete = instance.delete(filePaths(id));
        if(!isDelete){
            throw new NullPointerException(" 유저 삭제 실패");
        }
    }
}
