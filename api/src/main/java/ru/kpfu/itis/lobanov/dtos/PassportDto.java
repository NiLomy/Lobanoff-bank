package ru.kpfu.itis.lobanov.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PassportDto {
    private String id;
    private Short series;
    private Integer number;
    private Date birthday;
    private Character gender;
    private String departmentCode;
    private String issuedBy;
    private Date issuedDate;
    private String address;
}