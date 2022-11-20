package com.deals.feignClient;

import com.deals.dto.CreditDTO;
import com.deals.dto.LoanApplicationRequestDTO;
import com.deals.dto.LoanOfferDTO;
import com.deals.dto.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(url = "https://localhost:8080")
public interface ClientSide {

    @PostMapping("/offers")
    ResponseEntity<List<LoanOfferDTO>> offers(@RequestBody LoanApplicationRequestDTO requestDTO);

    @PostMapping("/calculate")
    ResponseEntity<CreditDTO>offerLoanCalculation(@RequestBody ScoringDataDTO scoringDataDTO);


}
