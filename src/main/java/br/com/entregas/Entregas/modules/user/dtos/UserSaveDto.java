package br.com.entregas.Entregas.modules.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.user.enums.Role;

public record UserSaveDto(
                String id,
                @NotBlank(groups = GroupValidation.Create.class) @Size(max = 255) String name,
                @NotBlank(groups = {
                                GroupValidation.Create.class,
                                GroupValidation.Login.class }) @Size(max = 255) @Email String email,
                @NotNull(groups = GroupValidation.Create.class) Role role,
                String photo,
                Boolean valid,
                Boolean actived){

        public Collection<? extends GrantedAuthority> getAuthorities() {

                switch (role) {
                        case ADMIN:
                                return List.of(
                                                new SimpleGrantedAuthority("ROLE_ADMIN"),
                                                new SimpleGrantedAuthority("ROLE_CUTOMER"),
                                                new SimpleGrantedAuthority("ROLE_BUSINESSPERSON"));

                        case BUSINESSPERSON:
                                return List.of(new SimpleGrantedAuthority("ROLE_BUSINESSPERSON"),
                                                new SimpleGrantedAuthority("ROLE_CUSTOMER"));

                        case CUSTOMER:
                                return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
                        default:
                                return List.of(new SimpleGrantedAuthority("ROLE_NENHUM"));

                }

        }
}