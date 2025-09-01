package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUser implements UserService {
    private final List<User>userData;
    public JCFUser(){
        userData = new ArrayList<>();
    }
    public void createUser(String username, int age , String email) {
        User newUser =new User(username,age,email);
        userData.add(newUser);
    };
    public User findUserByEmail(String userEmail) {

        return userData.stream()
                .filter(user -> user.getEmail().equals(userEmail))
                .findAny()
                .orElse(null);
    }

    public User findUserById(UUID id){

        return userData.stream()
                .filter(user -> user.getUserId().equals(id))
                .findAny()
                .orElse(null);
    };

    public List<User> findAllUsers(){
        if(userData.isEmpty()){
            throw new NullPointerException("현재 유저 없습니다.");
        }
        return userData;
    };

    public void updateUser(User user){
        int idx = userData.indexOf(user);
        if(idx == -1){
            throw new NullPointerException("해당 유저 없음");
        }
        userData.set(idx, user);
    };

    public void deleteUser(UUID id){
        User user = findUserById(id);
        if(user == null){
            throw new NullPointerException("삭제 오류 발생");
        }
        userData.remove(user);
    };
}
