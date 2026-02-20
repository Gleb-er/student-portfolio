package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.Section;
import com.example.student_portfolio.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByConference(Conference conference);
}