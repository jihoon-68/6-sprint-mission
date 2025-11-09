package com.sprint.mission.discodeit.channelservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
public class ChannelRepository {

    @Autowired
    private ChannelRepository channelRepository;
}
