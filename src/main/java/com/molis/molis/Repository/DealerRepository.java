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

    List<Dealer> findByActiveTrueAndDeletedFalse();

    Optional<Dealer> findByNamaDealer(String namaDealer);

    boolean existsByNamaDealer(String namaDealer);

    List<Dealer> findByNamaDealerAndActiveTrueAndDeletedFalse(String namaDealer);

//    List<Dealer> findAllByNamaDealer(String namaDealer);
}


