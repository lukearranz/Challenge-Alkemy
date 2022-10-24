package com.challenge.alkemy.entity.dto.authDto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {

    private String username;
    private String password;
    private String email;

}
