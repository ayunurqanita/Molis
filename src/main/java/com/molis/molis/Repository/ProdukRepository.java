package com.molis.molis.Repository;

import com.molis.molis.Model.Dealer;
import com.molis.molis.Model.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProdukRepository extends JpaRepository<Produk, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Produk d SET d.deleted = true WHERE d.produkId = :produkId")
    void softDeleteById(@Param("produkId") Integer produkId);

    List<Produk> findByActiveTrueAndDeletedFalseOrActiveFalseAndDeletedFalse();

    @Query("SELECT p FROM Produk p WHERE ((p.active = true AND p.deleted = false) OR (p.active = false AND p.deleted = false)) AND p.namaProduk = :namaProduk")
    List<Produk> findActiveProduksByName(@Param("namaProduk") String namaProduk);

    @Query("SELECT p FROM Produk p WHERE " +
            "((p.active = true AND p.deleted = false) OR (p.active = false AND p.deleted = false)) AND " +
            "(LOWER(p.namaProduk) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.merkId.namaMerk) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.estimasiJarak) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.kapasitasBaterai) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.ketahananBaterai) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.dayaMax) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.linkWebsiteProduk) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.kecepatanMax) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Produk> findActiveProductsBySearchTerm(@Param("searchTerm") String searchTerm);
}
