package ru.kpfu.itis.lobanov.data.mappers.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.Passport;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.PassportDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PassportMapper implements Mapper<Passport, PassportDto> {
    @Override
    public PassportDto toResponse(Passport entity) {
        if (entity == null) return null;

        return PassportDto.builder()
                .id(String.valueOf(entity.getId()))
                .name(entity.getName())
                .lastname(entity.getLastname())
                .patronymic(entity.getPatronymic())
                .series(entity.getSeries())
                .number(entity.getNumber())
                .birthday(entity.getBirthday())
                .gender(entity.getGender())
                .departmentCode(entity.getDepartmentCode())
                .issuedBy(entity.getIssuedBy())
                .issuedDate(entity.getIssuedDate())
                .address(entity.getAddress())
                .build();
    }

    @Override
    public List<PassportDto> toListResponse(List<Passport> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
