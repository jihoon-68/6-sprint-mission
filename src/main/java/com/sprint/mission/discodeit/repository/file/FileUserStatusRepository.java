package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class FileUserStatusRepository implements UserStatusRepository {

    private final String filePath = "userStatus.ser";

    @Override
    public UserStatus save(UserStatus userStatus) {

        Map<UUID, UserStatus> map = findAll();
        if(map == null){
            map =  new HashMap<>();
        }

        try (
                FileOutputStream fos = new FileOutputStream(filePath);
                BufferedOutputStream b = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(b)
        ) {
            map.put(userStatus.getId(), userStatus);
            oos.writeObject(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        Map<UUID, UserStatus> map = findAll();
        if(map == null || map.isEmpty() || map.containsKey(userId) == false){
            return Optional.empty();
        }

        return Optional.ofNullable(map.get(userId));
    }

    @Override
    public Map<UUID, UserStatus> findAll() {
        try (FileInputStream f = new FileInputStream(filePath);
             BufferedInputStream b = new BufferedInputStream(f);
             ObjectInputStream o = new ObjectInputStream(b)
        ) {
            Map<UUID, UserStatus> map = new HashMap<>();

            Object obj = o.readObject();
            Map<Object,Object> temp = (Map<Object, Object>) obj;
            for (Map.Entry<Object,Object> entry : temp.entrySet()) {
                UUID key = (UUID) entry.getKey();
                UserStatus value = (UserStatus) entry.getValue();
                map.put(key, value);
            }

            return map;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
