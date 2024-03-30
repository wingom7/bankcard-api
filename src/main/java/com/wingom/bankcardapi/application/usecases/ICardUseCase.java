package com.wingom.bankcardapi.application.usecases;

import com.wingom.bankcardapi.application.dto.CardBalanceOutputDTO;
import com.wingom.bankcardapi.application.dto.CardDTO;
import com.wingom.bankcardapi.application.dto.CardNumberOutputDTO;

import java.math.BigDecimal;

public interface ICardUseCase {
    CardNumberOutputDTO generateCardNumber(Long productCode);
    CardDTO activateCard(String cardNumber);
    CardDTO blockCard(String cardNumber);
    CardDTO rechargeCard(String cardId, BigDecimal balance);
    CardBalanceOutputDTO getCardBalance(String cardId);

}
