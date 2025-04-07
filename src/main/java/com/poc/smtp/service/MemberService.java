package com.poc.smtp.service;

import com.poc.smtp.dto.RegistrationDTO;
import com.poc.smtp.entity.Member;
import com.poc.smtp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

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


}
