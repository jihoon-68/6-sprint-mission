package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.User.CreateUserDTO;
import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.DTO.User.UpdateUserDTO;
import com.sprint.mission.discodeit.DTO.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;
    private final BinaryContentService binaryContentService;


    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String userCreate(@RequestParam List<MultipartFile> multipartFile, CreateUserDTO createUserDTO) {
        userService.create(multipartFile,createUserDTO);
        return "redirect:/user/all";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public String userUpdate(@RequestParam List<MultipartFile> multipartFile, UpdateUserDTO updateUserDTO){
        System.out.println(updateUserDTO);
        userService.update(multipartFile,updateUserDTO);
        return "redirect:/user/all";
    }

    @RequestMapping(value = "/find:{id}",  method = RequestMethod.GET)
    public String userFind(@PathVariable UUID id, Model model){
        FindUserDTO user = userService.find(id);
        BinaryContent binaryContent = binaryContentService.findById(user.profileId());

        model.addAttribute("user",user);
        model.addAttribute("binaryContent",binaryContent);
        return "User/updateUser";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String userDelete (@PathVariable UUID id){
        userService.delete(id);
        return "redirect:/user/all";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String findAll(Model model){
        model.addAttribute("users", userService.findAll());
        return "/User/userList";
    }

    @RequestMapping(value = "/status",method = RequestMethod.PUT)
    public String userStatusUpdate(@ModelAttribute UpdateUserStatusDTO updateUserStatusDTO){
        userStatusService.updateByUserId(updateUserStatusDTO);
        return "redirect:/user/all";
    }

}
