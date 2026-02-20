package com.example.student_portfolio.service;

import com.example.student_portfolio.model.Conference;
import com.example.student_portfolio.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    public Conference getCurrentConference() {
        // Получаем ближайшую предстоящую конференцию
        List<Conference> upcoming = conferenceRepository.findByStartDateAfter(LocalDateTime.now());
        if (!upcoming.isEmpty()) {
            return upcoming.get(0);
        }

        // Если нет предстоящих, возвращаем последнюю прошедшую
        List<Conference> past = conferenceRepository.findByStartDateBefore(LocalDateTime.now());
        if (!past.isEmpty()) {
            return past.get(past.size() - 1);
        }

        // Если конференций нет, создаем тестовую
        Conference defaultConference = new Conference();
        defaultConference.setTitle("XIX Всероссийская научно-практическая конференция");
        defaultConference.setDescription("Актуальные проблемы науки и техники");
        defaultConference.setStartDate(LocalDateTime.of(2026, 5, 15, 10, 0));
        defaultConference.setEndDate(LocalDateTime.of(2026, 5, 17, 18, 0));
        defaultConference.setApplicationDeadline(LocalDateTime.of(2026, 5, 1, 23, 59));
        defaultConference.setLocation("ОмГУПС, пр. Маркса 35");
        defaultConference.setOrganizer("Студенческое научное общество ОмГУПС");
        defaultConference.setContactEmail("sno@omgups.ru");
        defaultConference.setRequirements("Требования к оформлению статей будут добавлены позже");

        return conferenceRepository.save(defaultConference);
    }

    public List<Conference> getUpcomingConferences() {
        return conferenceRepository.findByStartDateAfter(LocalDateTime.now());
    }

    public Conference getConferenceById(Long id) {
        return conferenceRepository.findById(id).orElse(null);
    }

    public Conference saveConference(Conference conference) {
        return conferenceRepository.save(conference);
    }
}