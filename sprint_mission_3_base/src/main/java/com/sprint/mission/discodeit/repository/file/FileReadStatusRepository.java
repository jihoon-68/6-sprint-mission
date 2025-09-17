package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@ConditionalOnProperty(name = "repository.type", havingValue = "file")
public class FileReadStatusRepository implements ReadStatusRepository {
    private final String filePath = "readStatus.ser";

    @Override
    public ReadStatus save(ReadStatus readStatus) {
        Map<UUID, ReadStatus> map = findAll();
        if(map == null){
            map =  new HashMap<>();
        }

        map.put(readStatus.getId(), readStatus);
        saveAll(map);
        return readStatus;
    }

    @Override
    public Optional<ReadStatus> find(UUID id) {
        Map<UUID, ReadStatus> map = findAll();
        if(map == null || map.isEmpty()){
            return Optional.empty();
        }

        return Optional.ofNullable(map.get(id));
    }

    private void saveAll(Map<UUID, ReadStatus> map) {
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
    public Optional<List<ReadStatus>> findByChannelId(UUID ChannelId) {
        Map<UUID, ReadStatus> map = findAll();
        if(map == null || map.isEmpty()){
            return Optional.empty();
        }

        List<ReadStatus> status = map.entrySet().stream()
                .filter(x -> x.getValue().getChannelId().equals(ChannelId))
                .map(x -> x.getValue())
                .collect(Collectors.toUnmodifiableList());

        return Optional.ofNullable(status);
    }

    @Override
    public Optional<List<ReadStatus>> findByUserId(UUID userId) {
        Map<UUID, ReadStatus> map = findAll();
        if(map == null || map.isEmpty()){
            return Optional.empty();
        }

        List<ReadStatus> status = map.entrySet().stream()
                .filter(x -> x.getValue().getUserId().equals(userId))
                .map(x -> x.getValue())
                .collect(Collectors.toUnmodifiableList());

        return Optional.ofNullable(status);
    }

    @Override
    public Map<UUID, ReadStatus> findAll() {
        if(Files.exists(Path.of(filePath)) == false)
            return null;

        try (FileInputStream f = new FileInputStream(filePath);
             BufferedInputStream b = new BufferedInputStream(f);
             ObjectInputStream o = new ObjectInputStream(b)
        ) {
            Map<UUID, ReadStatus> map = new HashMap<>();

            Object obj = o.readObject();
            Map<Object,Object> temp = (Map<Object, Object>) obj;
            for (Map.Entry<Object,Object> entry : temp.entrySet()) {
                UUID key = (UUID) entry.getKey();
                ReadStatus value = (ReadStatus) entry.getValue();
                map.put(key, value);
            }

            return map;
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByChannelId(UUID ChannelId) {
        HashMap<UUID, ReadStatus> map = (HashMap<UUID, ReadStatus>) findAll();
        if(map == null || map.isEmpty())
            return;

        UUID id = map.entrySet().stream()
                .filter(x -> x.getValue().getChannelId().equals(ChannelId))
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
        HashMap<UUID, ReadStatus> map = (HashMap<UUID, ReadStatus>) findAll();
        if(map == null || map.isEmpty())
            return;

        if(map.containsKey(id) == false)
            return;

        map.remove(id);
        saveAll(map);
    }
}
