package com.sprint.mission.discodeit.repository.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

public class FileUserRepository implements UserRepository {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "users.ser";
    private Map<UUID, User> data = new HashMap<>();

    public FileUserRepository() {
        loadFromFile();
    }

    @Override
    public User createUser(String name, String email) {
        User user = new User(name, email);
        data.put(user.getId(), user);
        saveToFile();
        return user;
    }

    @Override
    public User getUserById(UUID id) {
        return data.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(data.values());
    }

    @Override
    public User updateUser(UUID id, String newName, String newEmail) {
        User user = data.get(id);
        if (user != null) {
            user.updateUser(newName, newEmail);
            saveToFile();
        }
        return user;
    }

    @Override
    public boolean deleteUser(UUID id) {
        boolean removed = data.remove(id) != null;
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return; // 파일이 없거나 비어있으면 새로 시작
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            data = (Map<UUID, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
            data = new HashMap<>(); // 오류 발생 시 데이터 맵을 초기화
        }
    }



}
