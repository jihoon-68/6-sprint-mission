package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    public JCFUserService(){
        this.data = new LinkedHashMap<>();
    }

    @Override
    public User create(User user){
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public User read(UUID id) {
        if (data.containsKey(id)) {
            return data.get(id);
        } else {
            System.out.println("해당 ID에 맞는 유저가 없습니다.");
            return null;
        }
    }

    @Override
    public List<User> readAll(){
        return new ArrayList<>(data.values());
    }

    @Override
    public User update(UUID id, String name, String email) {
        User user = data.get(id);
        if (user != null) {
            user.setUserName(name);
            user.setUserEmail(email);
        }
        return user;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }

}
