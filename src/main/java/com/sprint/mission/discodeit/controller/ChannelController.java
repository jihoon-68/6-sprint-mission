package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.Channel.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.FindChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.UpdateChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/channel")
@Controller
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @RequestMapping(value = "/public", method = RequestMethod.POST )
    public String createPublic(@ModelAttribute  CreatePublicChannelDTO createPublicChannelDTO) {
        channelService.createPublic(createPublicChannelDTO);
        return "redirect:/channelP";
    }

    @RequestMapping(value = "/private",method = RequestMethod.POST)
    public String createPrivate(@ModelAttribute CreatePrivateChannelDTO createPrivateChannelDTO) {
        channelService.createPrivate(createPrivateChannelDTO);
        return "redirect:/channelP";
    }

    @RequestMapping(value = "/find/{id}",  method = RequestMethod.GET)
    public String findById(@PathVariable UUID id, Model model) {
        FindChannelDTO channel = channelService.find(id);
        model.addAttribute("channel", channel.channel());
        model.addAttribute("recentMessage",channel.recentMessage());
        model.addAttribute("userId", channel.userId());
        return "Channel/updateChannel";
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public String update(@ModelAttribute UpdateChannelDTO updateChannelDTO) {
        channelService.update(updateChannelDTO);
        return "redirect:/channelP";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable UUID id) {
        channelService.delete(id);
        return "redirect:/channelP";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String findAll(@RequestParam("userId") UUID id, Model model) {
        List<FindChannelDTO> findChannelDTOS = channelService.findAllByUserId(id);
        model.addAttribute("channels",findChannelDTOS);
        return "Channel/channelList";
    }
}
