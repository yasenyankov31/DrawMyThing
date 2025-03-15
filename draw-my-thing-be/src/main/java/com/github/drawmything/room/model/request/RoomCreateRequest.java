package com.github.drawmything.room.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RoomCreateRequest(
    @NotNull @Min(30) @Max(32_766) Integer drawtime,
    @NotNull @Min(1) @Max(126) Integer rounds,
    @NotNull @Min(1) @Max(126) Integer wordsCount) {}
