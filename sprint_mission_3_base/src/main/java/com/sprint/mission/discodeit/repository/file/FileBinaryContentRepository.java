package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "repository.type", havingValue = "file")
public class FileBinaryContentRepository implements BinaryContentRepository {
    private final String filePath = "binaryContent.ser";

    @Override
    public BinaryContent save(BinaryContent binaryContent) {

        Map<UUID, BinaryContent> map = findAll();
        if(map == null){
            map =  new HashMap<>();
        }

        map.put(binaryContent.getId(), binaryContent);
        saveAll(map);

        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> find(UUID id) {
        Map<UUID, BinaryContent> map = findAll();
        if(map == null){
            map =  new HashMap<>();
        }

        return Optional.ofNullable(map.get(id));
    }

    private void saveAll(Map<UUID, BinaryContent> map) {
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
    public Optional<BinaryContent> findByUserId(UUID userId) {
        Map<UUID, BinaryContent> map = findAll();
        if(map == null || map.isEmpty() || map.containsKey(userId) == false){
            return Optional.empty();
        }

        return Optional.ofNullable(map.get(userId));
    }

    @Override
    public Map<UUID, BinaryContent> findAll() {
        if(Files.exists(Path.of(filePath)) == false)
            return null;

        try (FileInputStream f = new FileInputStream(filePath);
             BufferedInputStream b = new BufferedInputStream(f);
             ObjectInputStream o = new ObjectInputStream(b)
        ) {
            Map<UUID, BinaryContent> map = new HashMap<>();

            Object obj = o.readObject();
            Map<Object,Object> temp = (Map<Object, Object>) obj;
            for (Map.Entry<Object,Object> entry : temp.entrySet()) {
                UUID key = (UUID) entry.getKey();
                BinaryContent value = (BinaryContent) entry.getValue();
                map.put(key, value);
            }

            return map;
        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByUserId(UUID userId) {
        HashMap<UUID, BinaryContent> map = (HashMap<UUID, BinaryContent>) findAll();
        if(map == null || map.isEmpty())
            return;

        map.entrySet().removeIf(entry -> entry.getValue().getProfileId().equals(userId));
        saveAll(map);
    }

    @Override
    public void deleteByMessageId(UUID messageId) {
        HashMap<UUID, BinaryContent> map = (HashMap<UUID, BinaryContent>) findAll();
        if(map == null || map.isEmpty())
            return;

        map.entrySet().removeIf(entry -> entry.getValue().getMessageId().equals(messageId));
        saveAll(map);
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, BinaryContent> map = findAll();
        if(map == null){
            map =  new HashMap<>();
        }

        map.remove(id);
        saveAll(map);
    }
}
