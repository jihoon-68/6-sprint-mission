package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public class FileUserRepository implements UserRepository {
    private static final Path directory = Paths.get("./src/main/resources/UserDate");
    private static final FileEdit instance = new  FileEdit();

    private Path filePaths(UUID id) {
        return directory.resolve( id + ".ser");
    }

    public FileUserRepository() {
        instance.init(directory);
    }

    @Override
    public User save(User user) {
        instance.save(filePaths(user.getId()),user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {return instance.load(filePaths(id));}

    @Override
    public List<User> findAll() {
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
