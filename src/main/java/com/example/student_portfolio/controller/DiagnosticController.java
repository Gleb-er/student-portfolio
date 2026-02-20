package com.example.student_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DiagnosticController {

    @Autowired
    private Environment environment;

    @Autowired(required = false)
    private DataSource dataSource;

    @GetMapping("/diagnostic")
    public Map<String, Object> diagnostic() {
        Map<String, Object> diag = new HashMap<>();

        // Проверяем профили
        diag.put("activeProfiles", environment.getActiveProfiles());

        // Проверяем наличие DATABASE_URL (безопасно, без показа пароля)
        String dbUrl = environment.getProperty("DATABASE_URL");
        if (dbUrl != null) {
            String maskedUrl = dbUrl.replaceAll("://[^:]+:[^@]+@", "://***:***@");
            diag.put("DATABASE_URL exists", true);
            diag.put("DATABASE_URL (masked)", maskedUrl);
        } else {
            diag.put("DATABASE_URL exists", false);
        }

        // Проверяем DataSource
        if (dataSource != null) {
            try (Connection conn = dataSource.getConnection()) {
                diag.put("database connected", true);
                diag.put("database product", conn.getMetaData().getDatabaseProductName());
                diag.put("database version", conn.getMetaData().getDatabaseProductVersion());
            } catch (Exception e) {
                diag.put("database connected", false);
                diag.put("database error", e.getMessage());
            }
        } else {
            diag.put("dataSource", "not available");
        }

        return diag;
    }
}