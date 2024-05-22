package br.com.entregas.Entregas.core.config;

import java.io.IOException;
import java.util.Optional;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.entregas.Entregas.core.services.token.user.TokenUserService;
import br.com.entregas.Entregas.modules.user.dtos.UserSaveDto;
import br.com.entregas.Entregas.modules.user.dtos.mapper.UserMapper;
import br.com.entregas.Entregas.modules.user.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SecurityFilterConfig extends OncePerRequestFilter {

    private UserRepository userRepository;
    private TokenUserService tokenUserService;
    private UserMapper userMapper;

    private String recoveryToken(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            String token = recoveryToken(request);
            if (token != null) {
                String email = tokenUserService.validateToken(token);
                Optional<UserSaveDto> user = userRepository.findByEmail(email).map(u -> userMapper.toDto(u));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user.get(), null, user.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } 
    

}
