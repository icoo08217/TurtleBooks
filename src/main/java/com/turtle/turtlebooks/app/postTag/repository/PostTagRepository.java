package com.turtle.turtlebooks.app.postTag.repository;

import com.turtle.turtlebooks.app.postTag.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostTagRepository extends JpaRepository<PostTag , Long> {
    Optional<PostTag> findByPostIdAndPostKeywordId(Long postId , Long keywordId);

    List<PostTag> findAllByPostIdIn(long[] ids);

    List<PostTag> findAllByMemberIdAndPostKeyword_contentOrderByPost_idDesc(long id, String postKeywordContent);

    List<PostTag> findAllByPostId(long id);

    List<PostTag> findAllByMemberIdAndPostKeywordIdOrderByPost_idDesc(Long authorId, Long postKeywordId);
}
