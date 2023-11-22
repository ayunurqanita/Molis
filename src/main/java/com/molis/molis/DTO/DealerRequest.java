package com.molis.molis.DTO;

import com.molis.molis.Model.Merk;
import lombok.Data;

@Data
public class DealerRequest {
    private String namaDealer;
    private String alamat;
    private String kontak;
    private String linkWebsite;
    private String map;
    private String latitude;
    private String longitude;
    private Merk merkId;
    private String keterangan;

}
