package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    List<Conference> findByStartDateAfter(LocalDateTime date);
    List<Conference> findByStartDateBefore(LocalDateTime date);
}