package org.project.LibraryAccountingSystem.Spring.Boot.config;


import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonDetailsService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;
    private final HttpSessionConfig httpSessionConfig;
    private final PersonService personService;

    public SecurityConfig(PersonDetailsService personDetailsService, HttpSessionConfig httpSessionConfig, PersonService personService) {
        this.personDetailsService = personDetailsService;
        this.httpSessionConfig = httpSessionConfig;
        this.personService = personService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(personDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/menu-admin", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().hasAnyRole("LIBRARIAN", "ADMIN"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login").permitAll()
                        .loginProcessingUrl("/process_login")
                        .successHandler((request, response, authentication) -> {
                            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                            HttpSession session = request.getSession();
                            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
                            session.setAttribute("principal", personDetails.getPerson().getId());
                            personDetails.getPerson().setAuth(true);

                            httpSessionConfig.httpSessionListener().sessionCreated(new HttpSessionEvent(session));
                            if (roles.contains("ROLE_ADMIN")) {
                                response.sendRedirect("/menu-admin");
                            } else {
                                response.sendRedirect("/menu");
                            }
                        })
                        .failureForwardUrl("/auth/login?error"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"));

        return http.build();
    }
}
