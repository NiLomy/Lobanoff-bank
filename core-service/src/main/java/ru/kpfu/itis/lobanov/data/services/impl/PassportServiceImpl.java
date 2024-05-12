package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.data.entities.Passport;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.PassportRepository;
import ru.kpfu.itis.lobanov.data.services.PassportService;
import ru.kpfu.itis.lobanov.dtos.PassportDto;
import ru.kpfu.itis.lobanov.dtos.requests.SavePassportRequest;
import ru.kpfu.itis.lobanov.dtos.requests.UpdatePassportRequest;

@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;
    private final Mapper<Passport, PassportDto> passportMapper;

    @Override
    public PassportDto getById(Long id) {
        return passportMapper.toResponse(passportRepository.findById(id).orElse(null));
    }

    @Override
    public PassportDto save(SavePassportRequest request) {
        Passport passport = Passport.builder()
                .series(request.getSeries())
                .number(request.getNumber())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .departmentCode(request.getDepartmentCode())
                .issuedBy(request.getIssuedBy())
                .issuedDate(request.getIssuedDate())
                .address(request.getAddress())
                .build();

        return passportMapper.toResponse(passportRepository.save(passport));
    }

    @Override
    public PassportDto update(UpdatePassportRequest request) {
        Passport passport = passportRepository.findById(request.getId()).orElseThrow(IllegalArgumentException::new);

        passport.setSeries(request.getSeries());
        passport.setNumber(request.getNumber());
        passport.setBirthday(request.getBirthday());
        passport.setGender(request.getGender());
        passport.setDepartmentCode(request.getDepartmentCode());
        passport.setIssuedBy(request.getIssuedBy());
        passport.setIssuedDate(request.getIssuedDate());
        passport.setAddress(request.getAddress());

        return passportMapper.toResponse(passportRepository.save(passport));
    }
}