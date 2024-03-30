package com.wingom.bankcardapi.controllers;

import com.wingom.bankcardapi.application.dto.TransactionAnnulmentDTO;
import com.wingom.bankcardapi.application.dto.TransactionOutputDTO;
import com.wingom.bankcardapi.application.dto.TransactionPurchaseDTO;
import com.wingom.bankcardapi.application.usecases.ITransactionUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private ITransactionUseCase transactionUseCase;

    @PostMapping("/purchase")
    public ResponseEntity<TransactionOutputDTO> createPurchase(@Valid @RequestBody TransactionPurchaseDTO transactionPurchaseDTO) {
        TransactionOutputDTO transactionOutputDTO = transactionUseCase.createPurchase(
                transactionPurchaseDTO.getCardId(),
                transactionPurchaseDTO.getPrice()
        );
        return new ResponseEntity<>(transactionOutputDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionOutputDTO> getTransaction(@PathVariable String transactionId) {
        TransactionOutputDTO transactionOutputDTO = transactionUseCase.getTransaction(transactionId);
        return new ResponseEntity<>(transactionOutputDTO, HttpStatus.OK);
    }

    @PostMapping("/anulation")
    public ResponseEntity<TransactionOutputDTO> annulTransaction(@RequestBody TransactionAnnulmentDTO transactionAnnulmentDTO) {
        TransactionOutputDTO transactionOutputDTO = transactionUseCase.annulTransaction(
                transactionAnnulmentDTO.getTransactionId(),
                transactionAnnulmentDTO.getCardId()
        );
        return new ResponseEntity<>(transactionOutputDTO, HttpStatus.OK);
    }

}
