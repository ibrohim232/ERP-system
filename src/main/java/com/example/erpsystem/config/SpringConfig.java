package com.example.erpsystem.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SpringConfig {
//    private final JwtService jwtService;
//    private final AuthenticationService authenticationService;

    private final String[] WHITE_LIST = {"/sign-up","/sign-in", "/book/get-all"};

//    @Bean
//    public SecurityFilterChain configure (HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(requestConfigurer -> {
//                    requestConfigurer
//                            .requestMatchers(WHITE_LIST).permitAll()
//                            .requestMatchers("/order/update-status","/order/get-all").hasRole("LIBRARIAN")
//                            .requestMatchers("/order/create","/order/update-count","/order/get-by-user-id").hasRole("USER")
//                            .anyRequest().authenticated();
//                })
//                .addFilterBefore(new JwtFilter(jwtService, authenticationService),
//                        UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement(sessionManagement -> {
//                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                })
//                .build();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager (HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(authService)
//                .passwordEncoder(passwordEncoder());
//        return authenticationManagerBuilder.build();
//    }



}
