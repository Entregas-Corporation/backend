package br.com.entregas.Entregas.modules.user.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.user.dtos.UserDetailDto;
import br.com.entregas.Entregas.modules.user.dtos.UserSaveDto;
import br.com.entregas.Entregas.modules.user.models.User;

@Component
public class UserMapper {
    public UserSaveDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserSaveDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getPhoto(),
                user.getValid(), user.getActived());
    }

    public UserDetailDto toDtoDetail(User user) {
        if (user == null) {
            return null;
        }
        return new UserDetailDto(user.getId(), user.getName(), user.getEmail(), user.getPhoto(), user.getRole(),
                user.getCreated(), user.getUpdated());
    }

    public User toEntity(UserSaveDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();

        if (userDto.id() != null) {
            user.setId(userDto.id());
        }
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPhoto(userDto.photo());
        user.setRole(userDto.role());
        user.setValid(userDto.valid());
        user.setActived(userDto.actived());
        user.setUpdated(LocalDateTime.now());

        return user;
    }

}
