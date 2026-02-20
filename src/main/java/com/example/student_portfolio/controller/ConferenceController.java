package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.*;
import com.example.student_portfolio.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/conference")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ConferenceApplicationService applicationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    private Student getCurrentStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return studentService.findByEmail(email);
    }

    @GetMapping
    public String conferenceInfo(Model model) {
        Conference conference = conferenceService.getCurrentConference();
        model.addAttribute("conference", conference);
        return "conference/info";
    }

    @GetMapping("/sections")
    public String sections(Model model) {
        List<Section> sections = sectionService.getSectionsForCurrentConference();

        // Если секций нет, создаем тестовые
        if (sections.isEmpty()) {
            sectionService.createDefaultSections();
            sections = sectionService.getSectionsForCurrentConference();
        }

        model.addAttribute("sections", sections);
        return "conference/sections";
    }

    @GetMapping("/requirements")
    public String requirements(Model model) {
        Conference conference = conferenceService.getCurrentConference();
        model.addAttribute("conference", conference);
        return "conference/requirements";
    }

    @GetMapping("/apply")
    public String showApplicationForm(Model model, @RequestParam(required = false) Long sectionId) {
        Student currentStudent = getCurrentStudent();
        Conference conference = conferenceService.getCurrentConference();

        // Проверяем, не подавал ли студент уже заявку на эту конференцию
        if (applicationService.hasAppliedToConference(currentStudent, conference)) {
            return "redirect:/conference/my-application";
        }

        List<Section> sections = sectionService.getSectionsForCurrentConference();

        ConferenceApplication application = new ConferenceApplication();
        model.addAttribute("application", application);
        model.addAttribute("conference", conference);
        model.addAttribute("sections", sections);

        return "conference/apply";
    }

    @PostMapping("/apply")
    public String submitApplication(@ModelAttribute ConferenceApplication application,
                                    @RequestParam Long sectionId,
                                    @RequestParam("articleFile") MultipartFile file) {
        try {
            Student currentStudent = getCurrentStudent();
            Conference conference = conferenceService.getCurrentConference();
            Section section = sectionService.getSectionById(sectionId);

            // Сохраняем файл статьи
            if (!file.isEmpty()) {
                String uploadDir = "uploads/articles/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);

                application.setFilePath(filePath.toString());
            }

            application.setStudent(currentStudent);
            application.setConference(conference);
            application.setSection(section);
            application.setStatus("PENDING");

            applicationService.saveApplication(application);

            return "redirect:/conference/success";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/conference/apply?error";
        }
    }

    @GetMapping("/my-application")
    public String myApplication(Model model) {
        Student currentStudent = getCurrentStudent();
        Conference conference = conferenceService.getCurrentConference();
        ConferenceApplication application = applicationService.getApplicationByStudentAndConference(currentStudent, conference);

        if (application == null) {
            return "redirect:/conference/apply";
        }

        model.addAttribute("application", application);
        return "conference/my-application";
    }

    @GetMapping("/success")
    public String success() {
        return "conference/success";
    }
}