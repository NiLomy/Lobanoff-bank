package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.Requisites;
import ru.kpfu.itis.lobanov.data.mappers.UserMapper;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequisitesMapper {
    private final UserMapper userMapper;

    public RequisitesDto toResponse(Requisites requisites) {
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
        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
