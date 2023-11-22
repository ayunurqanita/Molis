package com.molis.molis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DealerDto {

    private String namaDealer;
    private String alamat;
    private String kontak;
    private String linkWebsite;
    private String map;
    private String latitude;
    private String longitude;
    private Integer merkId;
    private String keterangan;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private boolean active = true;
    private boolean deleted = false;




}

