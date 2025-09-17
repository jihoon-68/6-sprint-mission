package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileReadStatusRepository implements ReadStatusRepository {
    private final List<ReadStatus> readStatuses;

    public FileReadStatusRepository() {
        this.readStatuses = loadReadStatuses();
    }

    private List<ReadStatus> loadReadStatuses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/main/resources/readStatus.ser"))) {
            return (List<ReadStatus>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void exploreReadStatuses(List<ReadStatus> readStatuses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./src/main/resources/readStatus.ser"))) {
            oos.writeObject((Object) readStatuses);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return readStatuses.stream().filter(readStatus -> readStatus.getUserId().equals(userId)).toList();
    }

    @Override
    public void save(ReadStatus newStatus) {
        ReadStatus oldStatus = readStatuses.stream().filter(readStatus ->  readStatus.getUserId().equals(newStatus.getUserId())).findFirst().orElse(null);
        if (oldStatus == null) {
            readStatuses.add(newStatus);
        } else {
            oldStatus.updateReadAt();
        }
        exploreReadStatuses(readStatuses);
    }

    @Override
    public void deleteByChannelId(UUID id) {
        readStatuses.removeIf(readStatus -> readStatus.getChannelId().equals(id));
        exploreReadStatuses(readStatuses);
    }

    @Override
    public ReadStatus findById(UUID id) {
        return readStatuses.stream().filter(readStatus -> readStatus.getUserId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void deleteById(UUID id) {
        readStatuses.removeIf(readStatus -> readStatus.getUserId().equals(id));
        exploreReadStatuses(readStatuses);
    }
}
