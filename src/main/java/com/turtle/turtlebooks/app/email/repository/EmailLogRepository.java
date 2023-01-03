package com.turtle.turtlebooks.app.email.repository;

import com.turtle.turtlebooks.app.email.entity.SendEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<SendEmailLog , Long> {
}
