package com.example.student_portfolio;

import com.example.student_portfolio.service.ConferenceService;
import com.example.student_portfolio.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentPortfolioApplication implements CommandLineRunner {

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private SectionService sectionService;

	public static void main(String[] args) {
		SpringApplication.run(StudentPortfolioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Создаем тестовую конференцию при запуске
		conferenceService.getCurrentConference();

		// Создаем тестовые секции
		sectionService.createDefaultSections();
	}
}