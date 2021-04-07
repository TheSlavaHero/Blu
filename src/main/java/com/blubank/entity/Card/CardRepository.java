package com.blubank.entity.Card;

import com.blubank.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository  extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c where c.cardHolder.id = :id ")
    List<Card> allCards(@Param("id") Long id);

    void deleteCardById(Long id);

    @Query("SELECT c FROM Card c where c.id = :id ")
    Card getCardById(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE Cards c SET balance = ?2 where id = ?1", nativeQuery = true)
    void setBalance(@Param("id") Long id,
                           @Param("balance") double balance);

}


