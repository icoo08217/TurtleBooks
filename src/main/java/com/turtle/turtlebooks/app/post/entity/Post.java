package com.turtle.turtlebooks.app.post.entity;

import com.turtle.turtlebooks.app.base.entity.BaseEntity;

import javax.persistence.Entity;


@Entity
public class Post extends BaseEntity {

    // 회원 번호
    private long memberId;

    private String subject;

    private String content;

    private String contentHtml;

}
