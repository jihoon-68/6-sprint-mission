package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.*;

public class FileUserRepository implements UserRepository {

    private final String filePath = "user.rep";
    private Map<UUID, User> users = new HashMap<>();

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            users = (Map<UUID, User>) ois.readObject();

        } catch (FileNotFoundException e) {
            users = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try(FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(users);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User readUser(UUID userId) {
        return users.get(userId);
    }

    @Override
    public void deleteUser(UUID userId) {
        users.remove(userId);
    }

    @Override
    public void updateUser(User user) {
        if(users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
        }
        else{
            throw new IllegalArgumentException("User does not exist");
        }
    }

    /// 유저객체만 전부 모아서 반환 -> User만 필요하지 UUID 키는 필요없음
    @Override
    public List<User> readAllUser() {
        /// Map.values()는 Collection<User>타입 반환
        return new ArrayList<>(users.values());
    }
}
