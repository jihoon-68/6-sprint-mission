package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {

    private final List<User> userList = new ArrayList<>();


    @Override
    public User read(String name) {
        for (User user : userList) {
            if (user.getName().equals(name)) {
                System.out.println("유저 이름: " + user.getName());
                System.out.println("유저 상태: " + user.getState());
                return user;
            }
        }
        System.out.println("해당 유저가 없습니다. ");
        return null;

    }

    @Override
    public User create(String name) {
        User user = new User(name);
        userList.add(user);
        return user;
    }

    @Override
    public List<User> allRead() {
        return userList;
    }

    @Override
    public void modify(String name) {
        Scanner sc = new Scanner(System.in);
        for (User user : userList) {
            if (user.getName().equals(name)) {
                System.out.println("어떤 이름으로 수정하겠습니까? ");
                String modifyUserName = sc.nextLine();
                user.setName(modifyUserName);
                break;
            }
        }
    }

    @Override
    public void delete(String name) {
        for (User user : userList) {
            if (user.getName().equals(name)) {
                userList.remove(user);
                break;
            }
        }
    }
}
