package br.com.entregas.Entregas.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private SecurityFilterConfig securityFilterConfig;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "user/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "user/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "user/list/valid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "user/list/invalid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "user/list/admin/invalid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "user/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "user/validate/account/**").permitAll()                        
                        .requestMatchers(HttpMethod.PATCH, "user/close/account/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "user/update/user/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "user/suspense/account/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "user/reactivate/account/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "user/update/role/**").hasAnyRole("ADMIN")
                        
                        .requestMatchers(HttpMethod.POST, "institute/register").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "institute/list/valid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "institute/list/invalid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "institute/list/user/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "institute/list/user/invalid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "institute/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "institute/update/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "institute/toggle/activity/**").hasAnyRole("USER")


                        )
                .addFilterBefore(securityFilterConfig, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
