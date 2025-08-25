package com.sprint.mission.discodeit.repository.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SaveAndLoadCommon {
    public void init(Path directory) {
        if(!Files.exists(directory)) {
            try {
                Files.createDirectory(directory);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create directory: " + directory, e);
            }
        } else if (!Files.isDirectory(directory)) {
            throw new RuntimeException("Path exists but is not a directory: " + directory);
        }
    }

    public <T> void save(Path path, T obj) {
        try(
                FileOutputStream fos = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save object to file: " + path, e);
        }
    }

    public <T> List<T> load(Path directory) {
        if(Files.exists(directory)){
            try{
                return Files.list(directory)
                        .map(path -> {
                            try(FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)) {
                                Object data = ois.readObject();
                                return (T) data;
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException("Failed to read object from file: " + path, e);
                            }
                        })
                        .toList();
            } catch (IOException e) {
                throw new RuntimeException("Failed to load objects from directory: " + directory, e);
            }
        } else {
            return new ArrayList<>();
        }
    }
}
