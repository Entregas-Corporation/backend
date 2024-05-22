package br.com.entregas.Entregas.modules.user.dtos;

import java.util.List;

public record UserPageDto(
        List<UserDetailDto> userDto, long totalElements, int totalPages) {
}
