package com.sprint.mission.discodeit.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
public class UserRepository {

    @Autowired
    private UserRepository userRepository;
}
