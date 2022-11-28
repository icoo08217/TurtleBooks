package com.turtle.turtlebooks.app.member.repository;

import com.turtle.turtlebooks.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member , Long> {
}
