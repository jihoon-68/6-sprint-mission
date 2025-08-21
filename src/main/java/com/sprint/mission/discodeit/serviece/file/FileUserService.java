package com.sprint.mission.discodeit.serviece.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.serviece.UserServiece;

import java.io.*;
import java.util.*;

public class FileUserService implements UserServiece{

    private final String filePath = "users.ser";
    private Map<UUID, User> users = new HashMap<>();

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try(FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis))
        {
            users = (Map<UUID, User>) ois.readObject();
        }
        catch (FileNotFoundException e){
            users = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        try(FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(users);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void createUser(User user) {
        users.put(user.getUserId(), user);
        saveUsers();
    }

    @Override
    public User readUser(UUID userId) {
        loadUsers();
        return users.get(userId);
    }

    @Override
    public void updateUser(User user) {
        loadUsers();
        if(users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            saveUsers();
        }
    }

    @Override
    public void deleteUser(UUID userId) {
        loadUsers();
        if(users.containsKey(userId)) {
            users.remove(userId);
            saveUsers();
        }
    }

    @Override
    public List<User> readAllInfo() {
        loadUsers();
        return new ArrayList<>(users.values());
    }
}
