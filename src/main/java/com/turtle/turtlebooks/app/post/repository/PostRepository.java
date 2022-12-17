package com.turtle.turtlebooks.app.post.repository;

import com.turtle.turtlebooks.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post , Long> {
    List<Post> findAllByAuthorIdOrderByIdDesc(long authorId);
}
