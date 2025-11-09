package com.sprint.mission.discodeit.messageservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
public class MessageRepository {

    @Autowired
    private MessageRepository messageRepository;
}
