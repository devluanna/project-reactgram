package com.react.infra.security;

import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher(antMatcher("/"))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(antMatcher(HttpMethod.POST, "/login")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/create")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/users")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/user/{id}")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/upload/{id}")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/dashboard/{idDashboard}")).permitAll()
                        .requestMatchers(antMatcher("/update/profile/{id}")).hasRole("USERGRAM")
                        .requestMatchers(antMatcher("/update/profile/image/{id}")).hasRole("USERGRAM")
                        .requestMatchers(antMatcher("/update/profile/password/{id}")).hasRole("USERGRAM")
                        .anyRequest().authenticated()

                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        //.addFilterBefore(corsFilter, SecurityFilter.class); // Adicione o filtro CORS
        return httpSecurity.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}