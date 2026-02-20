package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.Student;
import com.example.student_portfolio.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(@Valid @ModelAttribute("student") Student student,
                                  BindingResult result, Model model) {

        if (studentService.findByEmail(student.getEmail()) != null) {
            result.rejectValue("email", "error.student",
                    "Пользователь с таким email уже существует");
        }

        if (result.hasErrors()) {
            return "register";
        }

        studentService.registerStudent(student);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}