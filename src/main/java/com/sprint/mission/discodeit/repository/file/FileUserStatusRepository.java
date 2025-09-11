package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileUserStatusRepository implements UserStatusRepository {
    private final List<UserStatus> userStatuses;

    public FileUserStatusRepository() {
        this.userStatuses = loadUserStatuses();
    }

    private List<UserStatus> loadUserStatuses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/main/resources/userStatus.ser"))) {
            return (List<UserStatus>)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new  ArrayList<>();
        }
    }

    private void exploreUserStatuses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./src/main/resources/userStatus.ser"))) {
            oos.writeObject((Object) userStatuses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserStatus findByUserId(UUID userId) {
        return userStatuses.stream().filter(userStatus -> userStatus.getUserId().equals(userId)).findFirst().orElse(null);
    }

    @Override
    public void save(UserStatus newStatus) {
        UserStatus oldStatus = userStatuses.stream().filter(userStatus -> userStatus.getUserId().equals(newStatus.getUserId())).findFirst().orElse(null);
        if (oldStatus == null) {
            userStatuses.add(newStatus);
        } else {
            oldStatus.setLastLogin(newStatus.getLastLogin());
            oldStatus.setLogin(newStatus.isLogin());
        }
        exploreUserStatuses();
    }

    @Override
    public void deleteByUserId(UUID id) {
        userStatuses.removeIf(userStatus -> userStatus.getUserId().equals(id));
        exploreUserStatuses();
    }

    @Override
    public List<UserStatus> findAll() {
        return userStatuses;
    }
}
