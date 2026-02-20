package com.example.student_portfolio.config;

import com.example.student_portfolio.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // Публичные страницы - доступны всем
                .antMatchers("/", "/index", "/register", "/css/**", "/js/**").permitAll()
                .antMatchers("/conference", "/conference/sections", "/conference/requirements").permitAll()
                // ДОБАВЬТЕ ЭТУ СТРОКУ - разрешаем скачивание файлов всем
                .antMatchers("/files/**").permitAll()
                // Страницы, требующие авторизации
                .antMatchers("/conference/apply", "/conference/my-application", "/conference/success").authenticated()
                .antMatchers("/portfolio/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/portfolio/my")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .authenticationProvider(authenticationProvider());

        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}