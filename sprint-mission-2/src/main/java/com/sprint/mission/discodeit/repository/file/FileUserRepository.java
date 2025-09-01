package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUserRepository implements UserRepository {
    private static final Path directory = Paths.get("/Users/mac/IdeaProjects/6-sprint-mission/sprint-mission-2/src/main/resources/UserDate");
    private static final FileEdit instance = new  FileEdit();

    private Path filePaths(User user) {
        return directory.resolve(user.getUserId().toString() + ".ser");
    }

    public FileUserRepository() {
        instance.init(directory);
    }

    public void createUser(User user) {
        instance.save(filePaths(user),user);
    }

    public User findUserById(UUID id) {

        List<User> userList = instance.load(directory);
        return userList.stream()
                .filter(user -> user.getUserId().equals(id))
                .findAny()
                .orElse(null);
    }

    public User findUserByUserEmail(String userEmail) {
        List<User> userList = instance.load(directory);
        return userList.stream()
                .filter(user -> user.getEmail().equals(userEmail))
                .findAny()
                .orElse(null);

    }

    public List<User> findAllUsers() {
        return instance.load(directory);
    }

    public void updateUser(User user) {
        instance.save(filePaths(user),user);
    }

    public void deleteUser(UUID id) {
        User user = findUserById(id);
        boolean isDelete = instance.delete(filePaths(user));
        if(!isDelete){
            throw new NullPointerException(user.getEmail()+" 유저 삭제 실패");
        }
    }
}
