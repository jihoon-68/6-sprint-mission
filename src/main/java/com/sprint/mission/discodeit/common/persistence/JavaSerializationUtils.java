package com.sprint.mission.discodeit.common.persistence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardOpenOption.*;

public final class JavaSerializationUtils {

    private JavaSerializationUtils() {
    }

    public static <K, V> Map<K, V> readMap(
            Path path,
            Class<K> keyClazz,
            Class<V> valueClazz
    ) {
        try (
                FileChannel channel = FileChannel.open(path, READ);
                var in = new ObjectInputStream(Channels.newInputStream(channel));
                var _ = channel.lock(0, Long.MAX_VALUE, true)
        ) {
            Object o = in.readObject();
            return requireMap(keyClazz, valueClazz, o);
        } catch (NoSuchFileException _) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deserialization failed", e);
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

    public static <K, V> void writeMap(Path path, Map<K, V> data) {
        try (
                FileChannel channel = FileChannel.open(path, WRITE, CREATE, TRUNCATE_EXISTING);
                var out = new ObjectOutputStream(Channels.newOutputStream(channel));
                var _ = channel.lock()
        ) {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Serialization failed", e);
        }
    }
}
