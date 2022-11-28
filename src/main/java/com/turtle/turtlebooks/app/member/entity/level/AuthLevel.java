package com.turtle.turtlebooks.app.member.entity.level;

import lombok.Getter;

@Getter
public enum AuthLevel {

    NORMAL(3, "NORMAL") ,
    ADMIN(7, "ADMIN");

    AuthLevel(int code, String value) {
        this.code = code;
        this.value = value;
    }

    private int code;
    private String value;
}
