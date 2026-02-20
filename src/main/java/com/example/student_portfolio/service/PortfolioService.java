package com.example.student_portfolio.service;

import com.example.student_portfolio.model.Portfolio;
import com.example.student_portfolio.model.Student;
import com.example.student_portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    public Portfolio createOrUpdatePortfolio(Portfolio portfolio) {
        portfolio.setUpdatedAt(LocalDateTime.now());
        if (portfolio.getId() == null) {
            portfolio.setCreatedAt(LocalDateTime.now());
        }
        return portfolioRepository.save(portfolio);
    }

    public Portfolio findByStudent(Student student) {
        return portfolioRepository.findByStudent(student).orElse(null);
    }

    public boolean hasPortfolio(Student student) {
        return portfolioRepository.existsByStudent(student);
    }
}