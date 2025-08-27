package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final ChannelRepository channelRepository;

    public JCFChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public void createChannel(String channelName, UUID ownerId) {
        if (channelName.trim().isEmpty()) {
            System.out.println("[Error] 채널 이름은 1글자 이상 입력해주세요.");
            return;
        }

        Channel channel = new Channel(channelName, ownerId);
        channelRepository.save(channel);
        System.out.println("[Info] 채널이 생성되었습니다.");
    }

    @Override
    public void updateChannelName(UUID id, String channelName) {
        if (channelName.trim().isEmpty()) {
            System.out.println("[Error] 채널 이름은 1글자 이상 입력해주세요.");
            return;
        }

        if (channelRepository.existsById(id)) {
            Channel channel = channelRepository.findById(id);
            channel.updateChannelName(channelName);
            channelRepository.save(channel);

            System.out.println("[Info] 채널명이 수정되었습니다.");
        } else {
            System.out.println("[Error] 존재하지 않는 채널입니다.");
        }
    }

    @Override
    public void deleteChannelById(UUID id) {
        if (channelRepository.existsById(id)) {
            channelRepository.deleteById(id);
            System.out.println("[Info] 채널 삭제가 완료되었습니다.");
        } else {
            System.out.println("[Error] 채널 삭제에 실패했습니다.");
        }
    }

    @Override
    public void deleteChannelByOwnerId(UUID ownerId) {
        List<UUID> ids = channelRepository.findAll()
                .stream().filter(c -> c.getOwnerId().equals(ownerId))
                .map(Channel::getId).toList();

        for (UUID id : ids) {
            channelRepository.deleteById(id);
        }
    }

    @Override
    public List<Channel> findAllChannels() {
        return channelRepository.findAll();
    }

    @Override
    public Channel findChannelById(UUID id) {
        return channelRepository.findById(id);
    }

    @Override
    public List<Channel> findChannelByOwnerId(UUID id) {
        return channelRepository.findByOwnerId(id);
    }
}
