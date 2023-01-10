package com.turtle.turtlebooks.app.postkeyword.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.turtle.turtlebooks.app.postkeyword.entity.PostKeyword;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostKeywordRepositoryImpl implements PostKeywordRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostKeyword> getQslAllByAuthorId(Long authorId) {
        return null;
    }
}
