package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JCFChannelService implements ChannelService {
    private final List<Channel> channelList = new ArrayList<>();

    @Override
    public Channel read(String name){
        for (Channel channel : channelList) {
            if (channel.getName().equals(name)) {
                System.out.println(name + " 채널이 존재합니다");
                return channel;
            }
        }
        System.out.println(name + " 채널이 없습니다");
        return null;
    }

    @Override
    public Channel create(String name){
        Channel channel = new Channel(name);
        channelList.add(channel);
        return channel;
    }
    @Override
    public List<Channel> allRead(){
        return channelList;
    }
    @Override
    public void modify(String name){
        Scanner sc = new Scanner(System.in);
        for (Channel channel : channelList) {
            if(channel.getName().equals(name)){
                System.out.println("채널명 수정 입력: ");
                String msg = sc.nextLine();
                if(msg==null)    break;
                channel.setName(msg);
                System.out.println("채널명이 수정되었습니다: " + msg);
                break;
            }
        }
        System.out.println("수정이 올바르지 않습니다");
    }
    @Override
    public void delete(String name){
        for (Channel channel : channelList) {
            if (channel.getName().equals(name)) {
                channelList.remove(channel);
                break;
            }
        }
        System.out.println("삭제되지 않았습니다");
    }
}
