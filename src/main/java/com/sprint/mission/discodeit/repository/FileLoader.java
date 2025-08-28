package com.sprint.mission.discodeit.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository 인터페이스에 넣을 경우 JCFRepository에서도 구현해야 하므로 별도로 분리
 */
public class FileLoader {

    // 역직렬화 + 파일 로드 (단건)
    public static <T> T loadOne(Path path) {
        if (!Files.exists(path)) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
