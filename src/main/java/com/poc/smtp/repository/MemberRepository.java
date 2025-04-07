package com.poc.smtp.repository;

import java.util.Optional;
import com.poc.smtp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailId(String emailId);

}
