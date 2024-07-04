package br.com.entregas.Entregas.core.services.token.user;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.user.dtos.UserSaveDto;

@Service
public class TokenUserService {
    @Value("{api.security.token.secret}")
    private String secret;

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusYears(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateToken(UserSaveDto userDto) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(userDto.email())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (IllegalArgumentException e) {
            throw new DomainException(ExceptionMessageConstant.token(e.toString()));
        } catch (JWTCreationException e) {
            throw new DomainException(ExceptionMessageConstant.token(e.toString()));
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String tokenValidated = JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();
        return tokenValidated;
    }

}
