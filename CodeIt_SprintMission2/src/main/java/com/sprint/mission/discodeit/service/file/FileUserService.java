package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import java.io.*;
import java.util.*;


public class FileUserService implements Serializable, UserService {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "user.ser";
    private Map<UUID, User> data = new HashMap<UUID, User>();

    public FileUserService() {
        loadFormFile();
    }

    @Override
    public User createUser(String name, String email) {
        User user = new User(name, email);
        data.put(user.getId(), user);
        saveToFIle();
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
        if (user != null){
            user.updateUser(newName, newEmail);
            saveToFIle();
        }
        return user;
    }

    @Override
    public boolean deleteUser(UUID id) {
        boolean removed = data.remove(id) != null;
        if (removed){
            saveToFIle();
        }
        return removed;
    }

    private void saveToFIle() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))){
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFormFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))){
            data = (Map<UUID, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Error loading users from file: " + e.getMessage());
            data = new HashMap<>();
        }
    }

}
