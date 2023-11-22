package com.molis.molis.DTO;

import com.molis.molis.Model.Merk;
import lombok.Data;

@Data
public class ProdukRequest {
    private String namaProduk;
    private Merk merkId;
    private String estimasiJarak;
    private String kecepatanMax;
    private String kapasitasBaterai;
    private String ketahananBaterai;
    private String dayaMax;
    private String linkWebsiteProduk;
    private String logo;
    private String gambarProduk;
    private String keterangan;
}
