package com.deals.dto;

import com.deals.customEnum.Gender;
import com.deals.customEnum.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoringDataDTO {

        private BigDecimal amount;
        private Integer term;
        private String firstName;
        private String lastName;
        private String middleName;
        private Gender gender;
        private LocalDate birthdate;
        private String passportSeries;
        private String passportNumber;
        private LocalDate passportIssueDate;
        private String passportIssueBranch;
        private MaritalStatus maritalStatus;
        private Integer dependentAmount;
        private EmploymentDTO employment;
        private String account;
        private Boolean isInsuranceEnabled;
        private Boolean isSalaryClient;
}
