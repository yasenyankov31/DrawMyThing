package com.github.drawmything.user.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
    @NotBlank @Size(min = 3, max = 100) String username,
    @NotBlank @Size(min = 8) String password) {}
