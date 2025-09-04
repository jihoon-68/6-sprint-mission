package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.*;

public class FileUserRepository implements UserRepository {
    private final File file;

    public FileUserRepository(String filename) {
        this.file = new File(filename);
        if (!file.exists()) {
            saveData(new HashMap<>());
        }
    }

    @SuppressWarnings("unchecked")
    private Map<UUID, User> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, User>) ois.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("User 데이터 불러오기 실패", e);
        }
    }

    private void saveData(Map<UUID, User> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("User 데이터 저장 실패", e);
        }
    }

    @Override
    public User save(User user) {
        Map<UUID, User> data = loadData();
        data.put(user.getId(), user);
        saveData(data);
        return user;
    }

    @Override
    public User findById(UUID id) {
        return loadData().get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(loadData().values());
    }

    @Override
    public boolean deleteById(UUID id) {
        Map<UUID, User> data = loadData();
        if (data.remove(id) != null) {
            saveData(data);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        saveData(new HashMap<>());
    }
}

