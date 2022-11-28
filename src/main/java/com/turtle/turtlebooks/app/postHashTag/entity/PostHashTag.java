package com.turtle.turtlebooks.app.postHashTag.entity;

import com.turtle.turtlebooks.app.base.entity.BaseEntity;
import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.post.entity.Post;
import com.turtle.turtlebooks.app.postKeyWord.entity.PostKeyWord;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PostHashTag extends BaseEntity {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    private Member member;

    @ManyToOne
    private PostKeyWord postKeyWord;
}
