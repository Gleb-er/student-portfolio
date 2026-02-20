package com.example.student_portfolio.service;

import com.example.student_portfolio.model.Section;
import com.example.student_portfolio.model.Conference;
import com.example.student_portfolio.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ConferenceService conferenceService;

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public List<Section> getSectionsForCurrentConference() {
        Conference currentConference = conferenceService.getCurrentConference();
        return sectionRepository.findByConference(currentConference);
    }

    public Section getSectionById(Long id) {
        return sectionRepository.findById(id).orElse(null);
    }

    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public void createDefaultSections() {
        Conference conference = conferenceService.getCurrentConference();

        if (sectionRepository.findByConference(conference).isEmpty()) {
            Section section1 = new Section();
            section1.setName("Информационные технологии и системы");
            section1.setDescription("Секция посвящена вопросам разработки программного обеспечения, базам данных, искусственному интеллекту");
            section1.setRoom("Главный корпус, ауд. 315");
            section1.setMaxParticipants(20);
            section1.setConference(conference);
            sectionRepository.save(section1);

            Section section2 = new Section();
            section2.setName("Транспорт и транспортные системы");
            section2.setDescription("Вопросы эксплуатации транспорта, логистики, безопасности движения");
            section2.setRoom("Главный корпус, ауд. 210");
            section2.setMaxParticipants(15);
            section2.setConference(conference);
            sectionRepository.save(section2);

            Section section3 = new Section();
            section3.setName("Энергетика и энергосбережение");
            section3.setDescription("Проблемы энергоэффективности, альтернативной энергетики");
            section3.setRoom("Лабораторный корпус, ауд. 105");
            section3.setMaxParticipants(15);
            section3.setConference(conference);
            sectionRepository.save(section3);
        }
    }
}