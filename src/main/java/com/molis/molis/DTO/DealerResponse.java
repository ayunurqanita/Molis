package com.molis.molis.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.molis.molis.Model.Merk;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealerResponse {
    private Integer dealerId;
    private String namaDealer;
    private String alamat;
    private String kontak;
    private String linkWebsite;
    private String map;
    private String latitude;
    private String longitude;
    private String keterangan;
    private MerkResponse merk;

    public MerkResponse getMerk() {
        return merk;
    }

    public void setMerk(Merk merk) {
        if (merk != null) {
            MerkResponse merkResponse = new MerkResponse();
            merkResponse.setNamaMerk(merk.getNamaMerk());
            this.merk = merkResponse;
        }
    }

    public void setNamaMerk(String namaMerk) {
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
