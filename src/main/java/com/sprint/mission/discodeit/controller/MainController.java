package com.sprint.mission.discodeit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String defaultLocation() {
        return "main";
    }

    @GetMapping("loginP")
    public String login() {
        return "login";
    }
    @GetMapping("/channelP/public")
    public String channelPPublic() {
        return "Channel/createChannel";
    }
    @GetMapping("/channelP/private")
    public String channelPPrivate() {
        return "Channel/createPrivateChannel";
    }
    @GetMapping("/channelP/update")
    public String updateChannel() {
        return "Channel/updateChannel";
    }
    @GetMapping("/channelP")
    public String channelP() {
        return "Channel/channelList";
    }
    @GetMapping("/userP/create")
    public String userPCreate() {
        return "User/createUser";
    }
    @GetMapping("/userP/update")
    public String userPUpdate() {
        return "User/updateUser";
    }
    @GetMapping("/messageP/send")
    public String messagePSend() {
        return "Message/sendMessage";
    }@GetMapping("/messageP")
    public String messageList() {
        return "Message/messageList";
    }
    @GetMapping("/messageP/update")
    public String messagePUpdate() {
        return "Message/updateMessage";
    }
    @GetMapping("/readStatusP/create")
    public String createStatus() {
        return "ReadStatus/createStatus";
    }
    @GetMapping("/readStatusP/update")
    public String updateMessage() {
        return "ReadStatus/updateStatus";
    }
    @GetMapping("/readStatusP/getStatus")
    public String getStatus() {
        return "ReadStatus/StatusList";
    }

}
