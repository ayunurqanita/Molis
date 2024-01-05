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

    List<Merk> findByNamaPerusahaan(String nama);

    boolean existsByNamaMerk(String namaMerk);

    List<Merk> findByActiveTrueAndDeletedFalseOrActiveFalseAndDeletedFalse();

    @Query("SELECT m FROM Merk m WHERE ((m.active = true AND m.deleted = false) OR (m.active = false AND m.deleted = false)) AND m.namaMerk = :namaMerk")
    List<Merk> findActiveMerksByName(@Param("namaMerk") String namaMerk);

    @Query("SELECT m FROM Merk m WHERE " +
            "((m.active = true AND m.deleted = false) OR (m.active = false AND m.deleted = false)) AND" +
            "(LOWER(m.namaPerusahaan) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(m.linkWebsite) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(m.namaMerk) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Merk> findMerksBySearchTerm(@Param("searchTerm") String searchTerm);
}
