package com.molis.molis.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.molis.molis.Model.Merk;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdukResponse {
    private Integer produkId;
    private String namaProduk;
    private MerkResponse merk;
    private String estimasiJarak;
    private String kecepatanMax;
    private String kapasitasBaterai;
    private String ketahananBaterai;
    private String dayaMax;
    private String linkWebsiteProduk;
    private String logo;
    private String gambarProduk;
    private String keterangan;

    public MerkResponse getMerk() { return merk;}

    public void setMerk(Merk merk) {
        if (merk != null) {
            MerkResponse merkResponse = new MerkResponse();
            merkResponse.setNamaMerk(merk.getNamaMerk());
            this.merk = merkResponse;
        }
    }


    public static class MerkResponse {
        private String namaMerk;

        public void setNamaMerk(String namaMerk) {
            this.namaMerk = namaMerk;
        }

        public String getNamaMerk() {
            return namaMerk;
        }

    }
}
