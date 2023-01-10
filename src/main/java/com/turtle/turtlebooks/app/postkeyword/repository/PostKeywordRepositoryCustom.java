package com.turtle.turtlebooks.app.postkeyword.repository;

import com.turtle.turtlebooks.app.postkeyword.entity.PostKeyword;

import java.util.List;

public interface PostKeywordRepositoryCustom {
    List<PostKeyword> getQslAllByAuthorId(Long authorId);
}
