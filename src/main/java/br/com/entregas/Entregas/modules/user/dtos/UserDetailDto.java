package br.com.entregas.Entregas.modules.user.dtos;

import java.time.LocalDateTime;

import br.com.entregas.Entregas.modules.user.enums.Role;



public record UserDetailDto(
                String id,
                String name,
                String email,
                String photo,
                Role role,
                LocalDateTime created,
                LocalDateTime updated) {

}
