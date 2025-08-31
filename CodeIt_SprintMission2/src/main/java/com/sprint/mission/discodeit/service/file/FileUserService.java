package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.*;

public class FileUserService implements UserService {
    private final File file;

    public FileUserService(String filename){
        this.file = new File(filename);
        if (!file.exists()){
            saveData(new HashMap<>());
        }
    }

    public void reset() {
        saveData(new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    private Map<UUID, User> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, User>) ois.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("불러오기 실패", e);
        }
    }

    private void saveData(Map<UUID, User> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("저장 실패", e);
        }
    }

    @Override
    public User create(User user) {
        Map<UUID, User> data = loadData();
        data.put(user.getId(), user);
        saveData(data);
        return user;
    }

    @Override
    public User read(UUID id) {
        return loadData().get(id);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(loadData().values()); // 모든 User 꺼내서 List로 반환
    }

    @Override
    public User update(UUID id, String newName, String newEmail) {
        Map<UUID, User> data = loadData();
        User user = data.get(id);
        if (user != null) {
            user.setUserName(newName);
            user.setUserEmail(newEmail);
            saveData(data);
        }
        return user;
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, User> data = loadData();
        if (data.remove(id) != null) {
            saveData(data);
            return true;
        }
        return false;
    }













}
