package com.deals.controller;

import com.deals.dto.LoanApplicationRequestDTO;
import com.deals.dto.LoanOfferDTO;
import com.deals.dto.ScoringDataDTO;
import com.deals.service.DealServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/deal")
public class LoanApplicationController {

    @Autowired
    DealServices dealServices;

    @PostMapping("/application")
   public ResponseEntity<?> createLoanApplication(@RequestBody LoanApplicationRequestDTO requestDTO)
    {
        return ResponseEntity.ok(dealServices.createApplication(requestDTO));

    }

    @PutMapping("/offer")
    public ResponseEntity<?> updateOfferDeals(@RequestBody LoanOfferDTO loanOfferDTO) {

        dealServices.offerDeals(loanOfferDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("calculate/{applicationId}")
    public ResponseEntity<?> dealCalculation(@RequestBody ScoringDataDTO scoringDataDTO, @PathVariable(value = "applicationId") Long applicationId)
    {
        dealServices.calculateLoan(scoringDataDTO, applicationId);
        return ResponseEntity.ok().build();
    }
}
