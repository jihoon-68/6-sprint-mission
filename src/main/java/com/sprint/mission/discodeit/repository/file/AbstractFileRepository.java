package com.sprint.mission.discodeit.repository.file;

import java.io.*;
import java.util.*;

abstract class AbstractFileRepository<T> {
    private final File file;

    protected AbstractFileRepository(File file) { this.file = file; }

    @SuppressWarnings("unchecked")
    protected Map<UUID, T> load() {
        if (!file.exists()) return new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            return (Map<UUID, T>) obj;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load repo: " + file, e);
        }
    }

    protected void save(Map<UUID, T> data) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save repo: " + file, e);
        }
    }
}