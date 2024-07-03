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
                        .requestMatchers(HttpMethod.GET, "user/close/account/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "user/suspense/account/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "user/reactivate/account/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "user/update/role/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "user/update/user/**").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "institute/register").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "institute/list/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "institute/list/invalid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "institute/list/user/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "institute/list/user/invalid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "institute/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "institute/toggle/activity/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "institute/update/**").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "product-category/register").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product-category/list/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product-category/list/invalid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "product-category/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product-category/toggle/activity/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "product-category/update/**").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "service/register").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "service/list/institute/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "service/list/institute/invalid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "service/list/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "service/list/invalid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "service/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "service/toggle/activity/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "service/update/**").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "product/register").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product/list/institute/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product/list/institute/invalid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product/list/product-category/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product/list/product-category/invalid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product/list/institute/product-category/valid/**")
                        .hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product/list/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product/list/invalid/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "product/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product/toggle/activity/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "product/update/**").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "treatment/register").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "treatment/list/support/resolved/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "treatment/list/support/not-resolved**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "treatment/list/complaint/resolved/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "treatment/list/complaint/not-resolved/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "treatment/list/institute/resolved/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "treatment/list/institute/not-resolved/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "treatment/detail/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "treatment/toggle/status-resolved/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "deliveryman/register").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "deliveryman/list/institute/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "deliveryman/list/institute/invalid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "deliveryman/list/user/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "deliveryman/list/user/invalid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "deliveryman/list/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "deliveryman/list/invalid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "deliveryman/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "deliveryman/toggle/activity/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "deliveryman/update/**").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "product-item/register").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product-item/list/user/valid/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "product-item/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "product-item/delete/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "product-item/update/**").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.GET, "order/list/user/canceled/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/list/user/delivered/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/list/user/sent/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/list/user/requested/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/list/institute/canceled/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/list/institute/delivered/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/list/institute/sent/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/list/institute/requested/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/status/canceled/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/status/delivered/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/status/sent/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/detail/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "order/toggle/activity/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "order/update/**").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "order-item/register").hasAnyRole("USER")

                )

                .addFilterBefore(securityFilterConfig, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
