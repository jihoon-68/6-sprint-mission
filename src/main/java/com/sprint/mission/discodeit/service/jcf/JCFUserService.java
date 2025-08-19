package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class JCFUserService implements UserService {
    private final Map<String, User> users;

    public JCFUserService()
    {
        users = new HashMap<String, User>();
    }

    @Override
    public boolean createUser(String userName,String email) {
        if(userName.isEmpty() ||  email.isEmpty())
        {
            System.out.println("Username or email is empty");
            return false;
        }

        if(users.containsKey(email))
        {
            System.out.println("User already exists");
            return false;
        }

        User user = new User(userName,email);
        users.put(email,user);

        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        if(user == null)
        {
            System.out.println("User is null");
            return false;
        }

        if(users.containsKey(user.getEmail()) == false)
        {
            System.out.println("User does not exists");
            return false;
        }

        users.remove(user.getEmail());
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        if(user == null)
        {
            System.out.println("User is null");
            return false;
        }

        if(users.containsKey(user.getEmail()) == false)
        {
            System.out.println("User does not exists");
            return false;
        }

        user.updateUpdatedAt();
        users.put(user.getEmail(),user);
        return true;
    }

    @Override
    public List<User> getUsers() {
        return users.entrySet()
                .stream()
                .map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(String email) {

        if(email.isEmpty())
        {
            System.out.println("Email is empty");
            return null;
        }

        if(users.containsKey(email) == false)
        {
            System.out.println("User does not exists");
            return null;
        }

        return users.get(email);
    }
}
