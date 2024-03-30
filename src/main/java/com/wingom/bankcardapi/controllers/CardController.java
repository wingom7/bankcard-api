package com.wingom.bankcardapi.controllers;

import com.wingom.bankcardapi.application.dto.CardDTO;
import com.wingom.bankcardapi.application.dto.CardNumberOutputDTO;
import com.wingom.bankcardapi.application.dto.CardBalanceOutputDTO;
import com.wingom.bankcardapi.application.dto.CardBalanceRechargeDTO;
import com.wingom.bankcardapi.application.dto.CardActivationInputDTO;
import com.wingom.bankcardapi.application.usecases.ICardUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("card")
public class CardController {

    @Autowired
    private ICardUseCase cardUseCase;

    @GetMapping("/{productId}/number")
    public ResponseEntity<CardNumberOutputDTO> generateCardNumber(@PathVariable Long productId) {
        CardNumberOutputDTO cardNumberOutputDTO = cardUseCase.generateCardNumber(productId);
        return new ResponseEntity<>(cardNumberOutputDTO, HttpStatus.OK);
    }

    @PostMapping("/enroll")
    public ResponseEntity<CardDTO> activateCard(@RequestBody CardActivationInputDTO cardActivationInputDTO) {
        CardDTO cardDTO = cardUseCase.activateCard(cardActivationInputDTO.getCardId());
        return new ResponseEntity<>(cardDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CardDTO> blockCard(@PathVariable String cardId) {
        CardDTO cardDTO = cardUseCase.blockCard(cardId);
        return new ResponseEntity<>(cardDTO, HttpStatus.OK);
    }

    @PostMapping("/balance")
    public ResponseEntity<CardDTO> rechargeCard(@RequestBody CardBalanceRechargeDTO cardBalanceRechargeDTO) {
        CardDTO cardDTO = cardUseCase.rechargeCard(cardBalanceRechargeDTO.getCardId(), cardBalanceRechargeDTO.getBalance());
        return new ResponseEntity<>(cardDTO, HttpStatus.OK);
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<CardBalanceOutputDTO> getCardBalance(@PathVariable String cardId) {
        CardBalanceOutputDTO balanceResponseDTO = cardUseCase.getCardBalance(cardId);
        return new ResponseEntity<>(balanceResponseDTO, HttpStatus.OK);
    }

}
