package com.wingom.bankcardapi.controllers;

import com.wingom.bankcardapi.application.dto.CardDTO;
import com.wingom.bankcardapi.application.dto.CardNumberOutputDTO;
import com.wingom.bankcardapi.application.dto.CardBalanceOutputDTO;
import com.wingom.bankcardapi.application.dto.CardBalanceRechargeDTO;
import com.wingom.bankcardapi.application.dto.CardActivationInputDTO;
import com.wingom.bankcardapi.application.usecases.ICardUseCase;
import com.wingom.bankcardapi.domain.enums.CardStatusEnum;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICardUseCase cardUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void generateCardNumberTest() throws Exception {
        Long productId = 1L;
        CardNumberOutputDTO cardNumberOutputDTO = new CardNumberOutputDTO("6234567890123456");

        when(cardUseCase.generateCardNumber(productId)).thenReturn(cardNumberOutputDTO);

        mockMvc.perform(get("/card/{productId}/number", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(cardNumberOutputDTO)));

        verify(cardUseCase, times(1)).generateCardNumber(productId);
    }

    @Test
    public void activateCardTest() throws Exception {
        CardActivationInputDTO inputDTO = new CardActivationInputDTO("5234567890123456");
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber("5234567890123456");
        cardDTO.setStatus(CardStatusEnum.ACTIVE);

        when(cardUseCase.activateCard(anyString())).thenReturn(cardDTO);

        mockMvc.perform(post("/card/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cardDTO)));

        verify(cardUseCase, times(1)).activateCard(anyString());
    }

    @Test
    public void blockCardTest() throws Exception {
        String cardId = "6234567890123456";
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(cardId);
        cardDTO.setStatus(CardStatusEnum.BLOCKED);

        when(cardUseCase.blockCard(cardId)).thenReturn(cardDTO);

        mockMvc.perform(delete("/card/{cardId}", cardId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cardDTO)));

        verify(cardUseCase, times(1)).blockCard(cardId);
    }

    @Test
    public void rechargeCardTest() throws Exception {
        CardBalanceRechargeDTO inputDTO = new CardBalanceRechargeDTO("6234567890123456", new BigDecimal("100.00"));
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber("6234567890123456");
        cardDTO.setBalance(new BigDecimal("100.00"));

        when(cardUseCase.rechargeCard(anyString(), any(BigDecimal.class))).thenReturn(cardDTO);

        mockMvc.perform(post("/card/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cardDTO)));

        verify(cardUseCase, times(1)).rechargeCard(anyString(), any(BigDecimal.class));
    }

    @Test
    public void getCardBalanceTest() throws Exception {
        String cardId = "6234567890123456";
        CardBalanceOutputDTO balanceDTO = new CardBalanceOutputDTO(new BigDecimal("100.00"));

        when(cardUseCase.getCardBalance(cardId)).thenReturn(balanceDTO);

        mockMvc.perform(get("/card/balance/{cardId}", cardId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(balanceDTO)));

        verify(cardUseCase, times(1)).getCardBalance(cardId);
    }


}

