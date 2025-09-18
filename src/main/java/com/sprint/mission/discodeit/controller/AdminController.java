package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequestDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserStatusService userStatusService;

    @RequestMapping("/admin/user/register")
    public ResponseEntity<Object> userRegister(@RequestBody UserCreateRequest userCreateRequest, @RequestBody Optional<BinaryContentCreateRequest> profileCreateRequest) {
        try{
            userService.create(userCreateRequest, profileCreateRequest);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/admin/user/update")
    public ResponseEntity<Object> userUpdate(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        try {
            userService.update(userUpdateRequestDto.getUserId(),
                    userUpdateRequestDto.getUserUpdateRequest(),
                    userUpdateRequestDto.getProfileCreateRequest()
            );
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("사용자가 존재하지 않습니다" + e.getMessage());
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/admin/user/delete/{userId}")
    public ResponseEntity<Object> userDelete(@PathVariable String userId) {
        try{
            userService.delete(UUID.fromString(userId));
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("사용자가 존재하지 않습니다" + e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/api/user/findAll")
    public ResponseEntity<Object> userList() {
        try{
            return ResponseEntity.ok().body(userService.findAll());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/admin/user/status/{userId}")
    public ResponseEntity<Object> updateUserStatus(@PathVariable String userId, @RequestBody UserStatusUpdateRequest userStatusUpdateRequest){
        try{
            userStatusService.updateByUserId(UUID.fromString(userId), userStatusUpdateRequest);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("사용자가 존재하지 않습니다" + e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
