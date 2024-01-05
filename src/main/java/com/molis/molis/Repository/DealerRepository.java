package com.molis.molis.Repository;


import com.molis.molis.Model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface DealerRepository extends JpaRepository<Dealer, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Dealer d SET d.deleted = true WHERE d.dealerId = :dealerId")
    void softDeleteById(@Param("dealerId") Integer dealerId);

    List<Dealer> findByActiveTrueAndDeletedFalseOrActiveFalseAndDeletedFalse();

    @Query("SELECT d FROM Dealer d WHERE " +
            "((d.active = true AND d.deleted = false) OR (d.active = false AND d.deleted = false)) AND" +
            "(LOWER(d.namaDealer) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.merkId.namaMerk) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.kontak) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.linkWebsite) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.map) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.latitude) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.longitude) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.alamat) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Dealer> findActiveDealersBySearchTerm(@Param("searchTerm") String searchTerm);




}


