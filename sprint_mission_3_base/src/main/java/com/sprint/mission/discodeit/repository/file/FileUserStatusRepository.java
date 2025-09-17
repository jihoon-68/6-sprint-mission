package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Repository
@ConditionalOnProperty(name = "repository.type", havingValue = "file")
public class FileUserStatusRepository implements UserStatusRepository {
    private final String filePath = "userStatus.ser";

    @Override
    public UserStatus save(UserStatus userStatus) {

        Map<UUID, UserStatus> map = findAll();
        if(map == null){
            map = new HashMap<>();
        }

        map.put(userStatus.getId(), userStatus);
        saveAll(map);
        return userStatus;
    }

    private void saveAll(Map<UUID, UserStatus> map) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath);
                BufferedOutputStream b = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(b)
        ) {
            oos.writeObject(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        Map<UUID, UserStatus> map = findAll();
        if(map == null || map.isEmpty()){
            return Optional.empty();
        }

        UserStatus status = map.entrySet().stream()
                .filter(x -> x.getValue().getUserId().equals(userId))
                .map(x -> x.getValue())
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found"));

        return Optional.ofNullable(status);
    }

    @Override
    public Optional<UserStatus> find(UUID id) {
        Map<UUID, UserStatus> map = findAll();
        if(map == null || map.isEmpty()){
            return Optional.empty();
        }

        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Map<UUID, UserStatus> findAll() {
        if(Files.exists(Path.of(filePath)) == false)
            return null;

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
        }
        catch (IOException | ClassNotFoundException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID userId) {
        HashMap<UUID, UserStatus> map = (HashMap<UUID, UserStatus>) findAll();
        if(map == null || map.isEmpty())
            return;

        UUID id = map.entrySet().stream()
                .filter(x -> x.getValue().getUserId().equals(userId))
                .map(x -> x.getValue().getId())
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found"));

        if(map.containsKey(id) == false)
            return;

        map.remove(id);
        saveAll(map);
    }

    @Override
    public void delete(UUID id) {
        HashMap<UUID, UserStatus> map = (HashMap<UUID, UserStatus>) findAll();
        if (map == null || map.isEmpty())
            return;

        if (map.containsKey(id) == false)
            return;

        map.remove(id);
        saveAll(map);
    }
}
