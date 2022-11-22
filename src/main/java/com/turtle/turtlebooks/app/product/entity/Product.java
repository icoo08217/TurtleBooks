package com.turtle.turtlebooks.app.product.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    // 회원 번호
    private long authorId;

    // 글 키워드 번호
    private long postKeywordId;

    private String subject;

    private int price;


}
