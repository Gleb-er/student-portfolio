package com.example.student_portfolio.service;

import com.example.student_portfolio.model.ConferenceApplication;
import com.example.student_portfolio.model.Student;
import com.example.student_portfolio.model.Conference;
import com.example.student_portfolio.repository.ConferenceApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConferenceApplicationService {

    @Autowired
    private ConferenceApplicationRepository applicationRepository;

    public ConferenceApplication saveApplication(ConferenceApplication application) {
        return applicationRepository.save(application);
    }

    public ConferenceApplication getApplicationByStudent(Student student) {
        List<ConferenceApplication> applications = applicationRepository.findByStudent(student);
        return applications.isEmpty() ? null : applications.get(0);
    }

    public ConferenceApplication getApplicationByStudentAndConference(Student student, Conference conference) {
        return applicationRepository.findByStudentAndConference(student, conference).orElse(null);
    }

    public boolean hasApplied(Student student) {
        return !applicationRepository.findByStudent(student).isEmpty();
    }

    public boolean hasAppliedToConference(Student student, Conference conference) {
        return applicationRepository.existsByStudentAndConference(student, conference);
    }

    public List<ConferenceApplication> getApplicationsByConference(Long conferenceId) {
        Conference conference = new Conference();
        conference.setId(conferenceId);
        return applicationRepository.findByConference(conference);
    }
}