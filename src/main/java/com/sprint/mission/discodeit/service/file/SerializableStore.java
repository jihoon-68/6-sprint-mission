package com.sprint.mission.discodeit.service.file;

import java.io.*;
import java.util.*;

class SerializableStore<T> {
    private final File file;

    SerializableStore(File file) { this.file = file; }

    Map<UUID, T> load() {
        if (!file.exists()) return new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            @SuppressWarnings("unchecked")
            Map<UUID, T> map = (Map<UUID, T>) obj;
            return map;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load file store: " + file, e);
        }
    }

    void save(Map<UUID, T> data) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file store: " + file, e);
        }
    }
}