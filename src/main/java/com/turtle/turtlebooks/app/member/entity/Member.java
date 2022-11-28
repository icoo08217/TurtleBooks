package com.turtle.turtlebooks.app.member.entity;

import com.turtle.turtlebooks.app.base.entity.BaseEntity;
import com.turtle.turtlebooks.app.member.entity.level.AuthLevel;

import javax.persistence.Convert;
import javax.persistence.Entity;

import static com.turtle.turtlebooks.app.member.entity.level.AuthLevel.*;


@Entity
public class Member extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private String email;

    // 권한 레벨 3 => 일반 사용자 , 레벨 7 => 관리자
    @Convert(converter = Converter.class)
    private AuthLevel authLevel;

}
