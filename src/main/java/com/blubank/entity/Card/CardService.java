package com.blubank.entity.Card;

import com.blubank.entity.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.blubank.entity.Card.RandomCardNumberGenerator.generateMasterCardNumbers;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public void addCard(User user, CardType cardType) {
        String[] numbers = generateMasterCardNumbers(1);
        Random rand = new Random();
        int upperbound = 1000;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        year += 4;
        calendar.set(1,year);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        String time = dateFormat.format(calendar.getTime());

        Card card = new Card(numbers[0], time, cardType, randCvc(), user, 0);
        user.addCard(card);
        cardRepository.save(card);
    }
    @Transactional
    public List<Card> allCards(User user) {
        return cardRepository.allCards(user.getId());
    }

    @Transactional
    public void deleteCards(long[] ids) {
        for (long id : ids) {
            cardRepository.deleteCardById(id);
        }
    }

    @Transactional
    public void topupCards(long[] ids, Double balance) {
        for (long id : ids) {
            Card card = cardRepository.getCardById(id);
            Double balanceDB;
            if (card.getBalance() == null) {balanceDB = 0.0;}
            else {balanceDB = card.getBalance();}
            cardRepository.setBalance(id, balance + balanceDB);
        }
    }

    public int randCvc() {
        Random rand = new Random();
        int number = rand.nextInt(1000);
        while (number < 100) {number = rand.nextInt(1000);}
        return number;
    }
}
