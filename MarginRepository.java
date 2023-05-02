package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Margin;

@Repository
public interface MarginRepository extends JpaRepository<Margin, Long> {
	@Query("SELECT m FROM Margin m WHERE (:instruction = '*' OR m.instruction = :instruction) AND (:baseCcy = '*' OR m.baseCcy = :baseCcy) AND (:termCcy = '*' OR m.termCcy = :termCcy) AND (:traderTier = 0 OR m.traderTier = :traderTier) AND ((m.fromAmount = 0 AND m.toAmount = 0) OR (:amount >= m.fromAmount AND :amount <= m.toAmount)) ORDER BY m.marginOrder ASC")
    Margin findFirstBySearchRequest(@Param("instruction") String instruction, @Param("baseCcy") String baseCcy, @Param("termCcy") String termCcy, @Param("traderTier") int traderTier, @Param("amount") int amount);

}

