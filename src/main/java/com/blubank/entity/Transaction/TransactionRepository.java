package com.blubank.entity.Transaction;

import com.blubank.entity.Card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    @Query("SELECT c FROM Transaction c where c.cardHolder.id = :id ")
//    List<Card> allCards(@Param("id") Long id);
}
