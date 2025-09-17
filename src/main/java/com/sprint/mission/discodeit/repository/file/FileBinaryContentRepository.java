package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class FileBinaryContentRepository implements BinaryContentRepository {
    private final List<BinaryContent> binaryContents;

    public  FileBinaryContentRepository() {
        binaryContents = loadBinaryContents();
    }

    private List<BinaryContent> loadBinaryContents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/main/resources/binaryContents.ser"))) {
            return (List<BinaryContent>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void exploreBinaryContents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./src/main/resources/binaryContents.ser"))) {
            oos.writeObject((Object) binaryContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BinaryContent findByUserId(UUID userId) {
        return binaryContents.stream().filter(binaryContent -> binaryContent.getUserId().equals(userId)).findFirst().orElse(null);
    }

    @Override
    public void save(BinaryContent newContent) {
        BinaryContent oldContent = binaryContents.stream().filter(binaryContent -> binaryContent.getId().equals(newContent.getId())).findFirst().orElse(null);

        if (oldContent == null) {
            binaryContents.add(newContent);
        } else {
            oldContent.setPath(newContent.getPath());
        }
        exploreBinaryContents();
    }

    @Override
    public List<BinaryContent> findByMessageId(UUID messageId) {
        return binaryContents.stream().filter(binaryContent -> Objects.equals(binaryContent.getMessageId(), messageId)).toList();
    }

    @Override
    public void deleteByUserId(UUID userId) {
        binaryContents.removeIf(binaryContent -> Objects.equals(binaryContent.getUserId(), userId));
        exploreBinaryContents();
    }

    @Override
    public void deleteByMessageId(UUID messageId) {
        binaryContents.removeIf(binaryContent -> Objects.equals(binaryContent.getMessageId(), messageId));
        exploreBinaryContents();
    }
}
