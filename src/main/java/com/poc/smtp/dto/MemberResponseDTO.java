package com.poc.smtp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poc.smtp.entity.Member;

public class MemberResponseDTO {

@JsonProperty("email")
private String emailId;

private String name;

private String mobileNo;

    public MemberResponseDTO() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @param member the member
     * @return the member response dto
     */

    public static MemberResponseDTO fromEntity(Member member) {

    MemberResponseDTO memberResponseDTO = new MemberResponseDTO();

    memberResponseDTO.setEmailId(member.getEmailId());
    memberResponseDTO.setName(member.getName());
    memberResponseDTO.setMobileNo(member.getMobileNo());

    return memberResponseDTO;

}

}
