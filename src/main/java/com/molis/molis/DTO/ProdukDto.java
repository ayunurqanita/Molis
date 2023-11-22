package com.molis.molis.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ProdukDto {

    private String namaProduk;
    private Integer merkId;
    private String estimasiJarak;
    private String kecepatanMax;
    private String kapasitasBaterai;
    private String ketahananBaterai;
    private String dayaMax;
    private String linkWebsiteProduk;
    private String logo;
    private String gambarProduk;
    private String keterangan;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private boolean active;
    private boolean deleted;

}
