package com.turtle.turtlebooks.app.postkeyword.entity;

import com.turtle.turtlebooks.app.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostKeyword extends BaseEntity {

    private String content;
    public Object getListUrl() {
        return "/post/tag" + content;
    }



}
