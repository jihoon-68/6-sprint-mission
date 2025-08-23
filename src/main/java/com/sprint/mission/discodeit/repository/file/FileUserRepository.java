package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUserRepository implements UserRepository {
    private final List<User> users;

    public FileUserRepository() {
        this.users = loadUsers();
    }

    private List<User> loadUsers() {
        try (FileInputStream fis = new FileInputStream("./src/main/resources/users.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return  new ArrayList<>();
        }
    }

    private void exploreUsers() {
        try (FileOutputStream fos = new FileOutputStream("./src/main/resources/users.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject((Object) users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(User user) {
        if (existsById(user.getId())) {
            User updateUser = findById(user.getId());
            updateUser.updateUsername(user.getUsername());
            updateUser.updatePassword(user.getPassword());
            updateUser.updateEmail(user.getEmail());
        } else {
            users.add(user);
        }
        exploreUsers();
    }

    @Override
    public void deleteById(UUID id) {
        users.removeIf(user -> user.getId().equals(id));
        exploreUsers();
    }

    @Override
    public User findById(UUID id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return users.stream().filter(u ->  u.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existsById(UUID id) {
        return users.stream().anyMatch(user -> user.getId().equals(id));
    }

    @Override
    public boolean existsByPassword(String password) {
        return users.stream().anyMatch(user -> user.getPassword().equals(password));
    }
}
