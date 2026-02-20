package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.Conference;
import com.example.student_portfolio.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping("/")
    public String home(Model model) {
        List<Conference> upcomingConferences = conferenceService.getUpcomingConferences();
        model.addAttribute("conferences", upcomingConferences);
        return "index";
    }
}