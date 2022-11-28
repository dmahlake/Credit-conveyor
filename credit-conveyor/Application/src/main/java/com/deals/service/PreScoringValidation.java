package com.deals.service;

import com.deals.dto.LoanApplicationRequestDTO;
import com.deals.exception.InvalidInputData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class PreScoringValidation {

    private String emailPattern = "^(.+)@(.+)$";
    private String nameCharacters = "^[A-Z][a-z]{2,}(?: [A-Z][a-z]*)*$";

    public void preScoring(LoanApplicationRequestDTO requestDTO)
    {
        if (!requestDTO.getFirstName().matches(nameCharacters) && !requestDTO.getLastName().matches(nameCharacters)) {
            throw new InvalidInputData("firstname or lastName entered is invalid");
        }
        if (requestDTO.getMiddleName() != null && !requestDTO.getMiddleName().matches(nameCharacters)) {
            throw new InvalidInputData("MiddleName cannot be null and characters are invalid");
        }
        if (!requestDTO.getEmail().matches(emailPattern)) {
            throw new InvalidInputData("Please enter a valid email address");
        }
        if (!requestDTO.getPassportSeries().matches("\\d{4}")) {
            throw new InvalidInputData("Passport series should be 4 digits number");
        }
        if (!requestDTO.getPassportNumber().matches("\\d{6}")) {
            throw new InvalidInputData("Passport number should be 6 digits number");
        }
        if (requestDTO.getAmount().compareTo(BigDecimal.valueOf(10000)) < 0) {
            throw new InvalidInputData("Loan amount requested shouldn't be less than 10000");
        }
        if (requestDTO.getTerm() < 6) {
            throw new InvalidInputData("Loan term should be greater than 6 months");
        }
        if (Period.between(requestDTO.getBirthdate() , LocalDate.now()).getYears() < 18){
            throw new InvalidInputData("Client should be 18 years and above");
        }
    }
}
