package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileUserService implements UserService{

    Path directory = Paths.get(System.getProperty("user.dir"), "UserData");

    public List<User> userData() {
        return FileInitSaveLoad.load(directory)
                .stream()
                .map(obj -> (User) obj) // User로 형 변환
                .collect(Collectors.toList()); // List로 다시 수집
    }

    @Override
    public User create(String name) {
        FileInitSaveLoad.init(directory);

        User user = new User(name);

        Path filePath = directory.resolve(user.getName().concat(".ser"));
        FileInitSaveLoad.save(filePath, user);

        return user;
    }

    @Override
    public User read(String name) {
        return userData().stream().filter(u->u.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public List<User> allRead() {
        return userData();
    }

    @Override
    public User modify(UUID id) {
        return userData().stream().filter(user->user.getCommon().getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public User delete(UUID id) {
        return userData().stream().filter(user->user.getCommon().getId().equals(id)).findAny().orElse(null);
    }

}
