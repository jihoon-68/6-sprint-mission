package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.User.CreateUserDTO;
import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.DTO.User.UpdateUserDTO;
import com.sprint.mission.discodeit.DTO.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/user/*")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = "multipart/form-data")
    public void userCreate(@RequestParam List<MultipartFile> multipartFile
            ,CreateUserDTO createUserDTO ){
        userService.create(multipartFile,createUserDTO);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public void userUpdate(@RequestParam List<MultipartFile> multipartFile,
                           UpdateUserDTO updateUserDTO){

        userService.update(multipartFile,updateUserDTO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, consumes = "application/json")
    public void userDelete (@PathVariable UUID id){
        userService.delete(id);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<FindUserDTO> findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "status",method = RequestMethod.PUT, consumes = "application/json")
    public void userStatusUpdate(@RequestBody UpdateUserStatusDTO updateUserStatusDTO){
        userStatusService.updateByUserId(updateUserStatusDTO);
    }

}
