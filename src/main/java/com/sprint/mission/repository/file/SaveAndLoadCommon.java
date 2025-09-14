package com.sprint.mission.repository.file;

import com.sprint.mission.entity.EntityCommon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SaveAndLoadCommon<T extends EntityCommon> {
    private final Class<T> clazz;
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";

    public SaveAndLoadCommon(Class<T> C) {
        this.clazz = (Class<T>) C;
        this.DIRECTORY = Path.of(System.getProperty("user.dir"), "file-data-map", C.getSimpleName());
        if (java.nio.file.Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected Path resolvePath(java.util.UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }

    public T save(T obj) {
        Path path = resolvePath(obj.getId());
        try (
                java.io.FileOutputStream fos = new java.io.FileOutputStream(path.toFile());
                java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos)
        ) {
            oos.writeObject(obj);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public Optional<T> load(UUID id) {
        Path path = resolvePath(id);
        if(!Files.exists(path)) return Optional.empty();
        try(
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            Object obj = ois.readObject();
            if(!clazz.isInstance(obj)) {
                throw new ClassCastException(obj.toString());
            }
            return Optional.of(clazz.cast(obj));
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public List<T> loadAll() {
        List<T> list = new java.util.ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(DIRECTORY, "*" + EXTENSION)) {
            for (Path path : stream) {
                try (
                        java.io.FileInputStream fis = new java.io.FileInputStream(path.toFile());
                        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis)
                ) {
                    Object obj = ois.readObject();
                    if(!clazz.isInstance(obj)) {
                        throw new ClassCastException(obj.toString());
                    }
                    list.add(clazz.cast(obj));
                } catch (java.io.IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean delete(UUID id) {
        Path path = resolvePath(id);
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
