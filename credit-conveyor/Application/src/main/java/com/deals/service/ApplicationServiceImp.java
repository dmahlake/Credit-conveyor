package com.deals.service;

import com.deals.dto.LoanApplicationRequestDTO;
import com.deals.dto.LoanOfferDTO;
import com.deals.feignClient.DealFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImp {

    PreScoringValidation preScoringValidation;

    private final DealFeignClient dealFeignClient;

    public List<LoanOfferDTO> applicationRequest(LoanApplicationRequestDTO requestDTO)
    {
        preScoringValidation.preScoring(requestDTO);
        List<LoanOfferDTO> loanOffer;
        loanOffer = dealFeignClient.createApplication(requestDTO).getBody();
        return loanOffer;
    }

    public void offer(LoanOfferDTO loanOfferDTO)
    {
        dealFeignClient.offerDeals(loanOfferDTO).getBody();
    }
}
