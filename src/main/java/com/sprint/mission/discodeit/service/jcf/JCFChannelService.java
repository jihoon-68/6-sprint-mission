package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final List<Channel> channels;

    public JCFChannelService() {
        channels = new ArrayList<>();
    }

    @Override
    public void createChannel(String channelName, UUID ownerId) {
        if (channelName.trim().isEmpty()) {
            System.out.println("[Error] 채널 이름은 1글자 이상 입력해주세요.");
            return;
        }
        if (channels.stream().anyMatch(c -> c.getChannelName().equals(channelName)
                && c.getOwnerId().equals(ownerId))) {
            System.out.println("[Error] 중복된 채널입니다.");
            return;
        }
        Channel channel = new Channel(channelName, ownerId);
        channels.add(channel);
        System.out.println("[Info] 채널이 생성되었습니다.");
    }

    @Override
    public void updateChannelName(UUID id, String channelName) {
        Channel existChannel = findChannelById(id);

        if (existChannel != null) {
            existChannel.updateChannelName(channelName);
            System.out.println("[Info} 채널명이 수정되었습니다.");
        } else {
            System.out.println("[Error] 존재하지 않는 채널입니다.");
        }
    }

    @Override
    public void deleteChannel(UUID id) {
        Channel channel = findChannelById(id);

        if (channel != null) {
            channels.remove(channel);
            System.out.println("[Info] 채널 삭제가 완료되었습니다.");
        } else {
            System.out.println("[Error] 채널 삭제에 실패했습니다.");
        }
    }

    @Override
    public List<Channel> findAllChannels() {
        return channels;
    }

    @Override
    public Channel findChannelById(UUID id) {
        return channels.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Channel findChannelByOwnerIdAndChannelName(UUID ownerId, String channelName) {
        return channels.stream().filter((c) -> {
            return c.getOwnerId().equals(ownerId) && c.getChannelName().equals(channelName);
        }).findFirst().orElse(null);
    }

    @Override
    public List<Channel> findChannelByOwnerId(UUID id) {
        return channels.stream().filter(c -> c.getOwnerId().equals(id)).toList();
    }
}
