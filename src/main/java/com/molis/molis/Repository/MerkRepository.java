package com.molis.molis.Repository;

import com.molis.molis.Model.Merk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MerkRepository extends JpaRepository<Merk,Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Merk d SET d.deleted = true WHERE d.merkId = :merkId")
    void softDeleteById(@Param("merkId") Integer merkId);

    List<Merk> findByNamaMerk(String namaMerk);

    List<Merk> findByNamaPerusahaan(String nama);

    List<Merk> findByActiveTrueAndDeletedFalse();

    boolean existsByNamaMerk(String namaMerk);
}
