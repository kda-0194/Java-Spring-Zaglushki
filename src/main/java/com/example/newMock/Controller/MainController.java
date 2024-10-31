package com.example.newMock.Controller;


import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController

public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try{
            String clientId = requestDTO.getClientId();
            char firstDigital = clientId.charAt(0);
            String currency;
            BigDecimal maxLimit;
            BigDecimal balance;
            String rqUID = requestDTO.getRqUID();

            if (firstDigital == '8'){
                currency = new String("US");
                balance = new BigDecimal(Math.random()*2000+1).setScale(2, RoundingMode.UP);
                maxLimit = new BigDecimal(2000);
            } else if (firstDigital == '9'){
                currency = new String("EU");
                balance = new BigDecimal(Math.random()*1000+1).setScale(2, RoundingMode.UP);
                maxLimit = new BigDecimal(1000);
            } else{
                currency = new String("RUB");
                balance = new BigDecimal(Math.random()*10000+1).setScale(2, RoundingMode.UP);
                maxLimit = new BigDecimal(10000);

            }
            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(responseDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("********** RequestDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("********** ResponseDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
