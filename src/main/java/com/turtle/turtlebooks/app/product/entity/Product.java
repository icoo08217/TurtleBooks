package com.turtle.turtlebooks.app.product.entity;

import com.turtle.turtlebooks.app.base.entity.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Product extends BaseEntity {

    // 회원 번호
    private long authorId;

    // 글 키워드 번호
    private long postKeywordId;

    private String subject;

    private int price;


}
