package com.deals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passport {

    public String series;
    private String number;
    private LocalDate issueDate;
    private String issueBranch;

    public Passport(String series, String number)
    {
        this.series = series;
        this.number = number;
    }
}
