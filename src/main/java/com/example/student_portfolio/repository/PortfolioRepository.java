package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.Portfolio;
import com.example.student_portfolio.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByStudent(Student student);
    boolean existsByStudent(Student student);
}