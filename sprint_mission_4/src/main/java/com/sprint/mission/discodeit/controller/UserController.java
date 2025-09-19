package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.domain.user.FileProcessingException;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/create", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@ModelAttribute UserCreateRequest user,
                                           @RequestParam(name = "profileImage", required = false) MultipartFile file) {
        var binOpt = toBinaryContentIfPresent(file);
        var created = userService.create(user, binOpt);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @RequestMapping(path = "/user/update/{id}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable UUID id,
                                           @ModelAttribute UserUpdateRequest user,
                                           @RequestParam(name = "profileImage", required = false) MultipartFile file) {
        var binOpt = toBinaryContentIfPresent(file);
        var updated = userService.update(id, user, binOpt);
        return ResponseEntity.ok(updated);
    }

    @RequestMapping(path = "/user/delete", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@RequestParam("id") UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    // FIXME: - GPT에서는 보안을 위해서 DTO를 반환하는게 좋다는데 어떤 게 맞는지 물어보기
//    @RequestMapping(path = "getAllUsers", method = RequestMethod.GET)
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        var users = userService.findAll();
//        return ResponseEntity.ok(users);
//    }

    @GetMapping("/api/user/findAll")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(path = "/user/updateStatus/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<UserDto> updateStatus(@PathVariable("id") UUID id) {
        userService.updatePresence(id);
        return ResponseEntity.noContent().build();
    }

    private Optional<BinaryContentCreateRequest> toBinaryContentIfPresent(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(new BinaryContentCreateRequest(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            ));
        } catch (IOException e) {
            throw new FileProcessingException("업로드 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }
}