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
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Member member = memberService.loginUser(loginDTO);
            MemberResponseDTO responseDTO = MemberResponseDTO.fromEntity(member);
            return RestUtil.success(responseDTO); // 200 OK
        }
        catch (IllegalArgumentException e) {

            // Use 401 if credentials are incorrect, 400 if it's validation (e.g., empty fields)
            String msg = e.getMessage();
            if ("Invalid email".equals(msg) || "Invalid password".equals(msg)) {
                return RestUtil.failure(msg, HttpStatus.UNAUTHORIZED); // 401
            }
            return RestUtil.failure(msg, HttpStatus.BAD_REQUEST); // 400
        }
        catch (Exception e) {
            return RestUtil.failure("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationDTO) {
        try {
            Member member = memberService.registerUser(registrationDTO);
            MemberResponseDTO responseDTO = MemberResponseDTO.fromEntity(member);
            return RestUtil.created(responseDTO);
        }
        catch (IllegalArgumentException e) {
            return RestUtil.failure(e.getMessage(), HttpStatus.BAD_REQUEST); //400
        }
        catch (Exception e) {
            return RestUtil.failure("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        try {
            memberService.forgotPassword(forgotPasswordDTO.getEmailId());
            return RestUtil.success("Reset password link sent to your registered email.");
        }
        catch (IllegalArgumentException e) {
            return RestUtil.failure(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return RestUtil.failure("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
