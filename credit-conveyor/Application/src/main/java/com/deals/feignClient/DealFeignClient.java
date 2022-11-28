package com.deals.feignClient;


import com.deals.dto.LoanApplicationRequestDTO;
import com.deals.dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "${api.url}")
public interface DealFeignClient {


    @PostMapping("/application")
    ResponseEntity<List<LoanOfferDTO>> createApplication(@RequestBody LoanApplicationRequestDTO requestDTO);

    @PostMapping("/offer")
    ResponseEntity<?>offerDeals(LoanOfferDTO request);

}
