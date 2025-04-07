package com.poc.smtp.service;

import com.poc.smtp.dto.LoginDTO;
import com.poc.smtp.dto.RegistrationDTO;
import com.poc.smtp.entity.Member;
import com.poc.smtp.entity.PasswordResetToken;
import com.poc.smtp.repository.MemberRepository;
import com.poc.smtp.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;


import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * checks if the email is already registered.
     *
     * @param emailId the email id
     * @return the boolean value
     */

    public boolean isEmailRegistered(String emailId) {
        return memberRepository.findByEmailId(emailId).isPresent();
    }


    /**
     * Register user member.
     *
     * @param registrationDTO the registration dto
     * @return the member
     */

    public Member registerUser(RegistrationDTO registrationDTO) {
        if (registrationDTO.getEmailId() == null || registrationDTO.getEmailId().trim().isEmpty()) {
            throw new IllegalArgumentException("Email ID cannot be null or empty");
        }

        // Check if the email is already registered
        if (isEmailRegistered(registrationDTO.getEmailId())) {
            throw new IllegalArgumentException("Email ID is already registered");
        }

        // Create a new member entity
        Member member = new Member();
        member.setEmailId(registrationDTO.getEmailId());
        member.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        member.setName(registrationDTO.getName());
        member.setMobileNo(registrationDTO.getMobileNo());

        // Save the member entity to the database
        return memberRepository.save(member);

    }

    public Member loginUser(LoginDTO loginDTO) {
        if (loginDTO.getEmailId() == null || loginDTO.getEmailId().trim().isEmpty()) {
            throw new IllegalArgumentException("Email ID cannot be null or empty");
        }

        if (loginDTO.getPassword() == null || loginDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        Member member = memberRepository.findByEmailId(loginDTO.getEmailId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return member;
    }

    public void forgotPassword(String email) {
        Member member = memberRepository.findByEmailId(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email: " + email));

        // Generate token
        String token = UUID.randomUUID().toString();

        // Expire in 15 minutes
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

        // Save token
        PasswordResetToken resetToken = new PasswordResetToken(token, member, expiry);
        tokenRepository.save(resetToken);

        // Compose reset link
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Requested!");
        message.setText("Please click the following link to reset your password, it is only valid for 15 minutes: " + resetLink + "Please contact support if you did not request this.");
        mailSender.send(message);
    }

}
