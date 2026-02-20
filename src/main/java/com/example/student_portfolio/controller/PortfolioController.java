package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.Portfolio;
import com.example.student_portfolio.model.Student;
import com.example.student_portfolio.service.PortfolioService;
import com.example.student_portfolio.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StudentService studentService;

    private Student getCurrentStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return studentService.findByEmail(email);
    }

    @GetMapping("/my")
    public String viewMyPortfolio(Model model) {
        Student currentStudent = getCurrentStudent();
        Portfolio portfolio = portfolioService.findByStudent(currentStudent);

        model.addAttribute("portfolio", portfolio);
        model.addAttribute("student", currentStudent);

        if (portfolio == null) {
            return "redirect:/portfolio/create";
        }

        return "portfolio/view";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("portfolio", new Portfolio());
        return "portfolio/create";
    }

    @PostMapping("/create")
    public String createPortfolio(@ModelAttribute Portfolio portfolio) {
        Student currentStudent = getCurrentStudent();
        portfolio.setStudent(currentStudent);
        portfolioService.createOrUpdatePortfolio(portfolio);
        return "redirect:/portfolio/my";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        Student currentStudent = getCurrentStudent();
        Portfolio portfolio = portfolioService.findByStudent(currentStudent);

        if (portfolio == null) {
            return "redirect:/portfolio/create";
        }

        model.addAttribute("portfolio", portfolio);
        return "portfolio/edit";
    }

    @PostMapping("/edit")
    public String updatePortfolio(@ModelAttribute Portfolio portfolio) {
        Student currentStudent = getCurrentStudent();
        Portfolio existingPortfolio = portfolioService.findByStudent(currentStudent);

        if (existingPortfolio != null) {
            portfolio.setId(existingPortfolio.getId());
            portfolio.setStudent(currentStudent);
            portfolio.setCreatedAt(existingPortfolio.getCreatedAt());
            portfolioService.createOrUpdatePortfolio(portfolio);
        }

        return "redirect:/portfolio/my";
    }
}