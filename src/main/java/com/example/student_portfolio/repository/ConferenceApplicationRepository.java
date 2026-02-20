package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.ConferenceApplication;
import com.example.student_portfolio.model.Student;
import com.example.student_portfolio.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ConferenceApplicationRepository extends JpaRepository<ConferenceApplication, Long> {
    List<ConferenceApplication> findByStudent(Student student);
    List<ConferenceApplication> findByConference(Conference conference);
    Optional<ConferenceApplication> findByStudentAndConference(Student student, Conference conference);
    boolean existsByStudentAndConference(Student student, Conference conference);
}