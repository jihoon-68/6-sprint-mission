package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.abstractservice.AbstractChannelService;

public class FileChannelService extends AbstractChannelService {
    public FileChannelService(ChannelRepository channelRepository) {
        super(channelRepository);
    }
}
