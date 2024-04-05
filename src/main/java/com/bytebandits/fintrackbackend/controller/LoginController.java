package com.bytebandits.fintrackbackend.controller;

import com.bytebandits.fintrackbackend.dto.LoginResponseDto;
import com.bytebandits.fintrackbackend.dto.UserDTO;
import com.bytebandits.fintrackbackend.model.User;
import com.bytebandits.fintrackbackend.service.LoginService;
import com.bytebandits.fintrackbackend.util.Converter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "${frontend.url}")
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public LoginController(LoginService loginService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        User user = loginService.createUser(userDTO.email(), userDTO.password());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        response.setHeader("Content-Type", "application/json");

        User user = (User) authentication.getPrincipal();

        UUID userId = user.getId();

        String jSessionId = response.getHeader("Set-Cookie").split(";")[0].split("=")[1];

        return ResponseEntity.ok(new LoginResponseDto(jSessionId, userId));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/test")
    public String testing() {
        return "Hello";
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> allUsers = loginService.getAllUsers();
        List<UserDTO> userResponseDtos = Converter.toUserResponseDtoList(allUsers);
        return ResponseEntity.ok(userResponseDtos);
    }
}
