package com.molis.molis.Service;


import com.molis.molis.DTO.ProdukDto;
import com.molis.molis.DTO.ProdukResponse;
import com.molis.molis.Model.Produk;

import java.util.List;

public interface ProdukService {
    Produk createProduk(ProdukDto produkDto);

    Produk updateProduk(Integer id, Produk updatedProduk);

    List<ProdukResponse> getAllProduk();

    ProdukResponse getProdukById(Integer produkId);

    void softDeleteById(Integer produkId);

    List<ProdukResponse> getActiveProduk();

    void toggleProdukStatus(Integer produkId);

    List<ProdukResponse> findActiveProductsBySearchTerm(String searchTerm);
}
