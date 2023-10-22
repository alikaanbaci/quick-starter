package com.alikaanbaci.quickstarter.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateRequest {

    @NotBlank(message = "Username can not be null or blank")
    private String username;

    @NotBlank(message = "Password can not be null or blank")
    private String password;
}
