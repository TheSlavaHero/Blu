package com.blubank.entity.Transaction;


import com.blubank.entity.Card.Card;
import com.blubank.entity.Card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;


    @Transactional
    public void performTransaction(String cardFromNumberString, String cardToNumberString, String amount) {
        Double amountDouble = Double.parseDouble(amount);

        Card cardFrom = cardRepository.getCardByNumber(cardFromNumberString);
        Card cardTo = cardRepository.getCardByNumber(cardToNumberString);

        cardRepository.setBalanceByCardNumber(cardFromNumberString, cardFrom.getBalance() - amountDouble);
        cardRepository.setBalanceByCardNumber(cardToNumberString, cardTo.getBalance() + amountDouble);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String time = dateFormat.format(calendar.getTime());
        Transaction transaction = new Transaction(cardFrom, cardTo, amountDouble, time);

        transactionRepository.save(transaction);
    }
}
