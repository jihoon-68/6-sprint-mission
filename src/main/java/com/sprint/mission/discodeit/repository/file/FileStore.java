package com.sprint.mission.discodeit.repository.file;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileStore<T> {
    private final File file;

    public FileStore(String path) { this.file = new File(path); }

    @SuppressWarnings("unchecked")
    public Map<UUID, T> loadMapOrEmpty() {
        if (!file.exists()) return new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, T>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public void saveMap(Map<?, T> data) {
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
