package com.turtle.turtlebooks.app.member.entity;

import com.turtle.turtlebooks.app.base.entity.BaseEntity;

import javax.persistence.Entity;


@Entity
public class Member extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private String email;

    // 권한 레벨 3 => 일반 사용자 , 레벨 7 => 관리자
//    private AuthLevel authLevel;

}
