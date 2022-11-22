package com.turtle.turtlebooks.app.post.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Id @GeneratedValue
    private long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    // 회원 번호
    private long memberId;

    private String subject;

    private String content;

    private String contentHtml;

}
