package com.turtle.turtlebooks.app.member.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Member {

    @Id @GeneratedValue
    private long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private String username;

    private String password;

    private String nickname;

    private String email;

    // 권한 레벨 3 => 일반 사용자 , 레벨 7 => 관리자
//    private AuthLevel authLevel;


}
