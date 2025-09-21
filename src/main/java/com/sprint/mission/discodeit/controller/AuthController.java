package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.Auth.LoginDTO;
import com.sprint.mission.discodeit.DTO.User.CreateUserDTO;
import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RequestMapping("/login")
@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String login(@ModelAttribute LoginDTO loginDTO) throws AuthenticationException {
       authService.login(loginDTO);
        return "redirect:/";
    }
}
