package com.sprint.mission.discodeit.service.abstractservice;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractUserService implements  UserService {

    protected final UserRepository userRepository;
    public AbstractUserService(UserRepository repository)
    {
        userRepository = repository;
    }

    @Override
    public boolean createUser(String userName,String email) {
        if(userName.isEmpty() ||  email.isEmpty())
        {
            System.out.println("Username or email is empty");
            return false;
        }

        User user = new User(userName,email);
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(User user) {
        if(user == null)
        {
            System.out.println("User is null");
            return false;
        }

        return userRepository.delete(user);
    }

    @Override
    public boolean updateUser(User user) {
        if(user == null)
        {
            System.out.println("User is null");
            return false;
        }

        return userRepository.update(user);
    }

    @Override
    public List<User> getUsers() {

        Map<String,User> users = userRepository.getAllUsers();
        if(users == null)
        {
            System.out.println("Users are null");
            return null;
        }

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

        Map<String,User> users = userRepository.getAllUsers();
        if(users == null)
        {
            System.out.println("Users are null");
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
