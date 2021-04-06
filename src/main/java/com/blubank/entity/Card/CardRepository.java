package com.blubank.entity.Card;

import com.blubank.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository  extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c where c.cardHolder.id = :id ")
    List<Card> allCards(@Param("id") Long id);
}


