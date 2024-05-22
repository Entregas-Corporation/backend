package br.com.entregas.Entregas.modules.user.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entregas.Entregas.core.dtos.token.user.UserTokenDto;
import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.user.dtos.UserDetailDto;
import br.com.entregas.Entregas.modules.user.dtos.UserPageDto;
import br.com.entregas.Entregas.modules.user.dtos.UserSaveDto;
import br.com.entregas.Entregas.modules.user.services.UserService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UserController {
    private UserService userService;

    @GetMapping("/list/valid")
    public UserPageDto findByAllUsersValid(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return userService.listValid(page, pageSize);
    }

    @GetMapping("/list/invalid")
    public UserPageDto findByAllUsersInvalid(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return userService.listInvalid(page, pageSize);
    }

    @GetMapping("/list/admin/invalid")
    public UserPageDto findByAllUsersInvalidByAdmin(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return userService.listInvalidByAdmin(page, pageSize);
    }

    @GetMapping("/detail/{id}")
    public UserDetailDto findUserById(@PathVariable String id) {
        return userService.detail(id);
    }

    @GetMapping("/validate/account/{id}")
    public String validateAccount(@PathVariable String id) {
        return userService.validateAccount(id);
    }

    @PatchMapping("/update/user/{id}")
    public UserSaveDto updateUser(@PathVariable String id, @RequestBody UserSaveDto saveUserDto) {
        return userService.updateByUser(id, saveUserDto);
    }

    @PatchMapping("/close/account/{id}")
    public UserSaveDto closeAccountUser(@PathVariable String id) {
        return userService.closeAccount(id);
    }

    @PatchMapping("/suspense/account/{id}")
    public UserSaveDto suspenseAccount(@PathVariable String id) {
        return userService.suspendAccount(id);
    }

    @PatchMapping("/reactivate/account/{id}")
    public UserSaveDto reactivateAccount(@PathVariable String id) {
        return userService.reactivateAccount(id);
    }

    @PatchMapping("/update/role/{id}")
    public UserSaveDto updateRole(@PathVariable String id, @RequestBody UserSaveDto saveUserDto) {
        return userService.changeRole(id, saveUserDto);
    }

    @PostMapping("/register")
    public UserSaveDto registerUser(@Validated(GroupValidation.Create.class) @RequestBody UserSaveDto userDto) {
        return userService.save(userDto);
    }

    @PostMapping("/login")
    public UserTokenDto loginUser(@Validated(GroupValidation.Login.class) @RequestBody UserSaveDto userDto) {
        return userService.login(userDto);
    }
}
