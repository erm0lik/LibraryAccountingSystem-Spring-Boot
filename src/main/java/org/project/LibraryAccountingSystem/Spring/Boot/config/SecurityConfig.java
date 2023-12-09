package org.project.LibraryAccountingSystem.Spring.Boot.config;

import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;
import java.util.Set;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String ADMIN = "ADMIN";
    private static final String LIBRARIAN = "LIBRARIAN";
    private static final String USER = "USER";
    private final PersonDetailsService personDetailsService;
    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, DataSource dataSource) {
        this.personDetailsService = personDetailsService;
        this.dataSource = dataSource;
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
        http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/menu-admin", "/admin/**").hasRole(ADMIN)
                        .requestMatchers("/requestBook/**", "/books/**", "/people/**").hasAnyRole(ADMIN, LIBRARIAN)
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/static/css/**").permitAll()

                        .anyRequest().hasAnyRole(ADMIN, LIBRARIAN, USER))
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login").permitAll()
                        .loginProcessingUrl("/process_login")
                        .successHandler((request, response, authentication) -> {
                            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


                            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

                            personDetails.getPerson().setAuth(true);
                            String role = "ROLE_";
                            if (roles.contains(role + ADMIN))
                                response.sendRedirect("/menu-admin");
                            else if (roles.contains(role + LIBRARIAN))
                                response.sendRedirect("/menu");
                            else if (roles.contains(role + USER)) {
                                response.sendRedirect("/user");
                            }
                        })
                        .failureUrl("/auth/login?error"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"))
                .rememberMe(rm -> rm
                        .tokenRepository(persistentTokenRepository())
                        .tokenValiditySeconds(24 * 60 * 60));


        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}
