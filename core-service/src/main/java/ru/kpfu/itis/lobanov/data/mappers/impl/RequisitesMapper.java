package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.Requisites;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequisitesMapper implements Mapper<Requisites, RequisitesDto> {
    private final Mapper<User, UserDto> userMapper;

    public RequisitesDto toResponse(Requisites requisites) {
        if (requisites == null) return null;

        return RequisitesDto.builder()
                .id(requisites.getId())
                .payee(userMapper.toResponse(requisites.getPayee()))
                .accountNumber(requisites.getPayeeAccount().getNumber())
                .code(requisites.getCode())
                .bankName(requisites.getBankName())
                .corrAccount(requisites.getCorrAccount())
                .build();
    }

    public List<RequisitesDto> toListResponse(List<Requisites> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
