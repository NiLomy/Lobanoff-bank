package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.Data;

import java.util.Date;

@Data
public class UpdatePassportRequest {
    private Long id;
    private Short series;
    private Integer number;
    private Date birthday;
    private Character gender;
    private String departmentCode;
    private String issuedBy;
    private Date issuedDate;
    private String address;
}
