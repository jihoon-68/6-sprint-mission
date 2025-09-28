package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.Auth.LoginDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@Tag(name = "Auth", description = "인증 API")
@RequestMapping("api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않음")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @PostMapping("/login")
    public ResponseEntity<User> login(
            @Schema(description = "로그인 정보")
            @RequestBody LoginDTO loginDTO) throws AuthenticationException {

        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginDTO));
    }
}
