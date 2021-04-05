package com.blubank.entity.Card;

import com.blubank.entity.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

import static com.blubank.entity.Card.RandomCardNumberGenerator.generateMasterCardNumbers;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public void addCard(User user, CardType cardType) {
        String[] numbers = generateMasterCardNumbers(1);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        year += 4;
        calendar.set(1,year);
        Card card = new Card(numbers[0], calendar, cardType, user);
        user.addCard(card);
        cardRepository.save(card);
    }
}
