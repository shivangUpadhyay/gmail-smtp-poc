package com.poc.smtp.controller;

import com.poc.smtp.dto.ForgotPasswordDTO;
import com.poc.smtp.dto.LoginDTO;
import com.poc.smtp.dto.MemberResponseDTO;
import com.poc.smtp.dto.RegistrationDTO;
import com.poc.smtp.entity.Member;
import com.poc.smtp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {

private final BCryptPasswordEncoder passwordEncoder;

@Autowired
private final MemberService memberService;

    public LoginController(MemberService memberService, BCryptPasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {

        return "Login successful for user: " + loginDTO.getEmailId();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegistrationDTO registrationDTO) {

        Member member = memberService.registerUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponseDTO.fromEntity(member));

    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {

        return "Password reset link sent to: " + forgotPasswordDTO.getEmailId();
    }


}
