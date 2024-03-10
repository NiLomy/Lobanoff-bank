package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum State {
    ACTIVE, BANNED
}
