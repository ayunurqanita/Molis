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
    private String updatedBy;
    private LocalDateTime updatedDate;

    private String message;
    private com.molis.molis.DTO.MerkResponse merkId;

    public MerkResponse getMerk() {
        return merk;
    }

    public void setMerk(Merk merk) {
        MerkResponse merkResponse = new MerkResponse();
        merkResponse.setNamaMerk(merk.getNamaMerk());
        // set properti lainnya sesuai kebutuhan

        this.merk = merkResponse;
    }

    public void setMerkId(com.molis.molis.DTO.MerkResponse merkId) {

        this.merkId = merkId;
    }

    public com.molis.molis.DTO.MerkResponse getMerkId() {
        return merkId;
    }


    public static class MerkResponse {
        private String namaMerk;


        // Setter for namaMerk
        public void setNamaMerk(String namaMerk) {
            this.namaMerk = namaMerk;
        }

        public String getNamaMerk() {
            return namaMerk;
        }


        // properti lainnya sesuai kebutuhan

        // constructors, getters, setters
    }



}
