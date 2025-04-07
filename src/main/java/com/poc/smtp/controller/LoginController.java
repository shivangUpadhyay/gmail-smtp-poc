package com.poc.smtp.controller;

import com.poc.smtp.dto.ForgotPasswordDTO;
import com.poc.smtp.dto.LoginDTO;
import com.poc.smtp.dto.MemberResponseDTO;
import com.poc.smtp.dto.RegistrationDTO;
import com.poc.smtp.entity.Member;
import com.poc.smtp.service.MemberService;
import com.poc.smtp.util.RestUtil;
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
    public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationDTO) {
        try {
            Member member = memberService.registerUser(registrationDTO);
            MemberResponseDTO responseDTO = MemberResponseDTO.fromEntity(member);
            return RestUtil.created(responseDTO);
        } catch (IllegalArgumentException e) {
            return RestUtil.failure(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return RestUtil.failure("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {

        return "Password reset link sent to: " + forgotPasswordDTO.getEmailId();
    }


}
