package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.abstractservice.AbstractChannelService;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class JCFChannelService extends AbstractChannelService {

    public JCFChannelService(ChannelRepository channelRepository)
    {
        super(channelRepository);
    }
}
