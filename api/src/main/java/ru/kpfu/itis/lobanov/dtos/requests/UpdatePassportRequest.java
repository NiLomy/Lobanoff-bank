package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.Data;

import java.util.Date;

@Data
public class UpdatePassportRequest {
    private String id;
    private String name;
    private String lastname;
    private String patronymic;
    private Short series;
    private Integer number;
    private Date birthday;
    private Character gender;
    private String departmentCode;
    private String issuedBy;
    private Date issuedDate;
    private String address;
}
