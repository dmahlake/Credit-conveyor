package com.deals.service;

import com.deals.customEnum.CreditStatus;
import com.deals.customEnum.Status;
import com.deals.dto.*;
import com.deals.exception.DataNotFoundException;
import com.deals.feignClient.ClientSide;
import com.deals.persistance.model.Application;
import com.deals.persistance.model.Client;
import com.deals.persistance.model.Credit;
import com.deals.persistance.repisitory.ApplicationRepo;
import com.deals.persistance.repisitory.ClientRepo;
import com.deals.persistance.repisitory.CreditRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServices {

    private final ClientRepo clientRepo;

    private final ApplicationRepo applicationRepo;

    private final ClientSide clientSide;
    private final CreditRepo creditRepo;


    private Client saveClientApplication(LoanApplicationRequestDTO requestDTO)
    {
        Client client = new Client();


        client.setFirstName(requestDTO.getFirstName());
        client.setLastName(requestDTO.getLastName());
        client.setMiddleName(requestDTO.getMiddleName());
        client.setEmail(requestDTO.getEmail());
        client.setDateOfBirth(requestDTO.getBirthdate());
        client.setPassport(new Passport(requestDTO.getPassportSeries(),
                requestDTO.getPassportNumber()));

        return clientRepo.save(client);
    }

    public void offerDeals(LoanOfferDTO request)
    {
        Application application = applicationRepo.findById(request.getApplicationId())
                .orElseThrow(()-> new DataNotFoundException("Sorry such application doesn't exist in our database"
                        + request.getApplicationId()));
        updateStatusHistory(application, Status.APPROVED);

        applicationRepo.save(application);
    }

    private void updateStatusHistory(Application application, Status status)
    {
        if(application.getStatus() == status)
        {
            application.setStatusHistory(Status.APPROVED.name());
        }
    }

    public List<LoanOfferDTO> createApplication(LoanApplicationRequestDTO requestDTO)
    {
        Client newClientApplication = saveClientApplication(requestDTO);

        Client save = clientRepo.save(newClientApplication);

        Application application = new Application();

        application.setClient(save);
        updateStatusHistory(application, Status.DOCUMENT_CREATED);

        applicationRepo.save(application);

        List<LoanOfferDTO> loan;
        loan = clientSide.offers(requestDTO).getBody();

        return loan;
    }

    public void calculateLoan(ScoringDataDTO scoringDataDTO, Long applicationId)
    {
        Application application = applicationRepo.findById(applicationId)
                .orElseThrow(()-> new DataNotFoundException("Sorry such application is doesn't exist in our database"
                        + applicationId));
        Client client = application.getClient();
        LoanOfferDTO appliedOffer = application.getAppliedOffer();

        scoringDataDTO.setAmount(appliedOffer.getRequestedAmount());
        scoringDataDTO.setTerm(appliedOffer.getTerm());
        scoringDataDTO.setFirstName(client.getFirstName());
        scoringDataDTO.setLastName(client.getLastName());
        scoringDataDTO.setMiddleName(client.getMiddleName());
        scoringDataDTO.setBirthdate(client.getDateOfBirth());
        scoringDataDTO.setPassportNumber(client.getPassport().getNumber());
        scoringDataDTO.setPassportSeries(client.getPassport().getSeries());
        scoringDataDTO.setPassportIssueBranch(client.getPassport().getIssueBranch());
        scoringDataDTO.setPassportIssueDate(client.getPassport().getIssueDate());
        scoringDataDTO.setIsInsuranceEnabled(appliedOffer.getIsInsuranceEnabled());
        scoringDataDTO.setIsSalaryClient(appliedOffer.getIsSalaryClient());

        CreditDTO creditDTO = clientSide.offerLoanCalculation(scoringDataDTO).getBody();
         Credit credit = new Credit();

         credit.setAmount(creditDTO.getAmount());
         credit.setTerm(creditDTO.getTerm());
         credit.setMonthlyPayment(creditDTO.getMonthlyPayment());
         credit.setRate(creditDTO.getRate());
         credit.setFlc(creditDTO.getPsk());
         credit.setIsInsuranceEnabled(creditDTO.getIsInsuranceEnabled());
         credit.setIsSalaryClient(creditDTO.getIsSalaryClient());
         credit.setCreditStatus(CreditStatus.CALCULATED);
         creditRepo.save(credit);

         updateStatusHistory(application, Status.APPROVED);
         applicationRepo.save(application);
    }

}
