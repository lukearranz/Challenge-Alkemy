package com.challenge.alkemy.entity.dto.authDto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class RegisterRequestDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

}
