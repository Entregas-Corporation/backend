package br.com.entregas.Entregas.modules.user.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.constants.SendEmailMessageConstant;
import br.com.entregas.Entregas.core.dtos.token.user.UserTokenDto;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.core.services.sendEmail.SendEmailService;
import br.com.entregas.Entregas.core.services.token.user.TokenUserService;
import br.com.entregas.Entregas.modules.user.dtos.UserDetailDto;
import br.com.entregas.Entregas.modules.user.dtos.UserPageDto;
import br.com.entregas.Entregas.modules.user.dtos.UserSaveDto;
import br.com.entregas.Entregas.modules.user.dtos.mapper.UserMapper;
import br.com.entregas.Entregas.modules.user.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenUserService tokenService;
    private SendEmailService sendEmailService;

    @Transactional
    public UserPageDto listValid(int page, int pageSize) {
        Page<UserDetailDto> userPage = userRepository.findByValidTrue(PageRequest.of(page, pageSize))
                .map(user -> userMapper.toDtoDetail(user));
        return new UserPageDto(userPage.getContent(), userPage.getTotalElements(), userPage.getTotalPages());
    }

    @Transactional
    public UserPageDto listInvalid(int page, int pageSize) {
        Page<UserDetailDto> userPage = userRepository.findByValidFalse(PageRequest.of(page, pageSize))
                .map(user -> userMapper.toDtoDetail(user));
        return new UserPageDto(userPage.getContent(), userPage.getTotalElements(), userPage.getTotalPages());
    }

    @Transactional
    public UserPageDto listInvalidByAdmin(int page, int pageSize) {
        Page<UserDetailDto> userPage = userRepository.findByActivedFalse(PageRequest.of(page, pageSize))
                .map(user -> userMapper.toDtoDetail(user));
        return new UserPageDto(userPage.getContent(), userPage.getTotalElements(), userPage.getTotalPages());
    }

    @Transactional
    public UserDetailDto detail(String id) {
        return userRepository.findById(id).map(user -> userMapper.toDtoDetail(user))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Usuário")));
    }

    @Transactional
    public UserSaveDto save(UserSaveDto userDto) {
        boolean emailUsed = userRepository.findByEmail(userMapper.toEntity(userDto).getEmail()).isPresent();
        if (emailUsed) {
            throw new DomainException(ExceptionMessageConstant.attributeUsed("E-mail"));
        }
        UserSaveDto newUserDto = new UserSaveDto(userDto.id(), userDto.name(), userDto.email(), userDto.role(),
                userDto.photo(), false, true);
        UserSaveDto saveUser = userMapper.toDto(userRepository.save(userMapper.toEntity(newUserDto)));
        sendEmailService.sendWellcomeAccount(saveUser);
        return saveUser;

    }

    @Transactional
    public UserTokenDto login(UserSaveDto userDto) {
        Optional<UserSaveDto> user = userRepository.findByEmail(userMapper.toEntity(userDto).getEmail())
                .map(u -> userMapper.toDto(u));
        if (user == null || user.get().valid() == false) {
            throw new DomainException(ExceptionMessageConstant.invalidAuthentication);
        }
        String token = tokenService.generateToken(user.get());
        return new UserTokenDto(token);
    }

    @Transactional
    public UserSaveDto updateByUser(String id, UserSaveDto saveUserDto) {
        return userRepository.findById(id).map(recordFound -> {

            if (saveUserDto.name() != null) {
                recordFound.setName(saveUserDto.name());
            }
            if (saveUserDto.valid() != null && saveUserDto.valid() == false && recordFound.getValid() == true) {
                recordFound.setValid(saveUserDto.valid());
            }
            return userRepository.save(recordFound);

        }).map(user -> userMapper.toDto(user))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Usuário")));
    }

    public UserSaveDto closeAccount(String id) {
        return userRepository.findById(id).map(recordFound -> {
            recordFound.setValid(false);
            sendEmailService.sendDisableAccount(userMapper.toDto(recordFound));
            return userRepository.save(recordFound);

        }).map(user -> userMapper.toDto(user))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Usuário")));
    }

    @Transactional
    public String validateAccount(String id) {
        UserSaveDto userSaveDto = userRepository.findById(id).map(recordFound -> {
            if (recordFound.getActived()) {
                recordFound.setValid(true);
            }
            return userRepository.save(recordFound);
        }).map(user -> userMapper.toDto(user))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Usuário")));
        if (userSaveDto.valid()) {
            sendEmailService.sendValidationAccount(userSaveDto);
            return SendEmailMessageConstant.successfullValidation;
        }
        return ExceptionMessageConstant.unsuccessfullValidation;
    }

    @Transactional
    public UserSaveDto suspendAccount(String id) {
        return userRepository.findById(id).map(recordFound -> {
            recordFound.setValid(false);
            recordFound.setActived(false);
            sendEmailService.sendDisableAccount(userMapper.toDto(recordFound));
            return userRepository.save(recordFound);
        }).map(user -> userMapper.toDto(user))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Usuário")));
    }

    @Transactional
    public UserSaveDto reactivateAccount(String id) {
        return userRepository.findById(id).map(recordFound -> {
            recordFound.setValid(true);
            recordFound.setActived(true);
            sendEmailService.sendValidationAccount(userMapper.toDto(recordFound));
            return userRepository.save(recordFound);
        }).map(user -> userMapper.toDto(user))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Usuário")));
    }

    @Transactional
    public UserSaveDto changeRole(String id, UserSaveDto saveUserDto) {
        return userRepository.findById(id).map(recordFound -> {
            if (saveUserDto.role() != null) {
                recordFound.setRole(saveUserDto.role());
            }
            return userRepository.save(recordFound);
        }).map(user -> userMapper.toDto(user))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Usuário")));
    }
}
