package com.wingom.bankcardapi.controllers;

import com.wingom.bankcardapi.application.dto.TransactionAnnulmentDTO;
import com.wingom.bankcardapi.application.dto.TransactionOutputDTO;
import com.wingom.bankcardapi.application.dto.TransactionPurchaseDTO;
import com.wingom.bankcardapi.application.usecases.ITransactionUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITransactionUseCase transactionUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createPurchaseTest() throws Exception {
        TransactionPurchaseDTO purchaseDTO = new TransactionPurchaseDTO();
        purchaseDTO.setCardId("6543267654568907");
        purchaseDTO.setPrice(new BigDecimal("100.00"));

        TransactionOutputDTO outputDTO = new TransactionOutputDTO();
        outputDTO.setTransactionNumber("976543");
        outputDTO.setCardNumber(purchaseDTO.getCardId());

        when(transactionUseCase.createPurchase(anyString(), any(BigDecimal.class))).thenReturn(outputDTO);

        mockMvc.perform(post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(outputDTO)));

        verify(transactionUseCase, times(1)).createPurchase(anyString(), any(BigDecimal.class));
    }

    @Test
    public void getTransactionTest() throws Exception {
        String transactionId = "897654";
        TransactionOutputDTO outputDTO = new TransactionOutputDTO();
        outputDTO.setTransactionNumber(transactionId);
        outputDTO.setCardNumber("5679087654343456");

        when(transactionUseCase.getTransaction(transactionId)).thenReturn(outputDTO);

        mockMvc.perform(get("/transaction/{transactionId}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(outputDTO)));

        verify(transactionUseCase, times(1)).getTransaction(transactionId);
    }

    @Test
    public void annulTransactionTest() throws Exception {
        TransactionAnnulmentDTO annulmentDTO = new TransactionAnnulmentDTO();
        annulmentDTO.setTransactionId("876543");
        annulmentDTO.setCardId("578656543489098");

        TransactionOutputDTO outputDTO = new TransactionOutputDTO();
        outputDTO.setTransactionNumber(annulmentDTO.getTransactionId());
        outputDTO.setCardNumber(annulmentDTO.getCardId());

        when(transactionUseCase.annulTransaction(anyString(), anyString())).thenReturn(outputDTO);

        mockMvc.perform(post("/transaction/anulation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(annulmentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(outputDTO)));

        verify(transactionUseCase, times(1)).annulTransaction(anyString(), anyString());
    }

}
