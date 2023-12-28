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

    Produk findByNamaProduk(String namaProduk);

    boolean existsByNamaProduk(String namaProduk);

    List<Produk> findByActiveTrueAndDeletedFalse();

    boolean existsByNamaProdukAndProdukIdNot(String namaProduk, Integer produkId);

    List<Produk> findAllByNamaProduk(String namaProduk);

    List<Produk> findByNamaProdukAndActiveTrueAndDeletedFalse(String namaProduk);
}
