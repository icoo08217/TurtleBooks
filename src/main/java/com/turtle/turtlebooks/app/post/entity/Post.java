package com.turtle.turtlebooks.app.post.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Post {

    @Id @GeneratedValue
    private long id;
}
