package com.turtle.turtlebooks.app.postTag.repository;

import com.turtle.turtlebooks.app.postTag.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag , Long> {
}
