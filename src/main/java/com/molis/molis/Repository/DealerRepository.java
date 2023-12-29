package com.molis.molis.Repository;


import com.molis.molis.Model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DealerRepository extends JpaRepository<Dealer, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Dealer d SET d.deleted = true WHERE d.dealerId = :dealerId")
    void softDeleteById(@Param("dealerId") Integer dealerId);

    List<Dealer> findByActiveTrueAndDeletedFalseOrActiveFalseAndDeletedFalse();

    @Query("SELECT d FROM Dealer d WHERE ((d.active = true AND d.deleted = false) OR (d.active = false AND d.deleted = false)) AND d.namaDealer = :namaDealer")
    List<Dealer> findActiveDealersByName(@Param("namaDealer") String namaDealer);
}


