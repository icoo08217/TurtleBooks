package com.turtle.turtlebooks.app.productTag.entity;

import com.turtle.turtlebooks.app.base.entity.BaseEntity;
import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.product.entity.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ProductTag extends BaseEntity {

    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @ToString.Exclude
    private ProductKeyword productKeyword;

}
