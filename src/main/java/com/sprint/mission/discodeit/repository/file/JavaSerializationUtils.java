package com.sprint.mission.discodeit.repository.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

final class JavaSerializationUtils {

    private static final String DESERIALIZATION_FAILED = "Deserialization failed";
    private static final String SERIALIZATION_FAILED = "Serialization failed";

    private JavaSerializationUtils() {
    }

    static <K, V> Map<K, V> readMap(
            Path path,
            Class<K> keyClazz,
            Class<V> valueClazz
    ) {
        try (var in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            Object o = in.readObject();
            return requireMap(keyClazz, valueClazz, o);
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(DESERIALIZATION_FAILED, e);
        }
    }

    private static <K, V> Map<K, V> requireMap(Class<K> keyClazz, Class<V> valueClazz, Object o) {
        if (!(o instanceof Map<?, ?> data)) {
            throw new ClassCastException();
        }
        Map<K, V> result = new HashMap<>();
        for (Object e : data.entrySet()) {
            if (!(e instanceof Map.Entry<?, ?> entry)) {
                throw new ClassCastException();
            }
            K key = keyClazz.cast(entry.getKey());
            V value = valueClazz.cast(entry.getValue());
            result.put(key, value);
        }
        return result;
    }

    static <K, V> void writeMap(Path path, Map<K, V> data) {
        try (var out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(SERIALIZATION_FAILED, e);
        }
    }
}
