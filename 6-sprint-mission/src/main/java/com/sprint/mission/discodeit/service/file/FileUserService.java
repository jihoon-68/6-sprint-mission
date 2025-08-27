package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileUserService implements UserService {

    Path directory = Paths.get(System.getProperty("user.dir"), "UserData");

    @Override
    public User create(String name) {
        FileInitSaveLoad.init(directory);

        User user = new User(name);

        Path filePath = directory.resolve(user.getName().concat(".ser"));
        FileInitSaveLoad.<User>save(filePath, user);

        return user;
    }

    @Override
    public User read(String name) {
        return allRead()
                .stream()
                .filter(u -> u.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<User> allRead() {
        return FileInitSaveLoad.<User>load(directory);
    }

    @Override
    public User modify(UUID id) {
        return allRead()
                .stream()
                .filter(user -> user.getCommon().getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public User delete(UUID id) {
        return allRead()
                .stream()
                .filter(user -> user.getCommon().getId().equals(id))
                .findAny()
                .orElse(null);
    }

}
