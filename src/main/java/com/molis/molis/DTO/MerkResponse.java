package com.molis.molis.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerkResponse {
    private String namaMerk;
    private String namaPerusahaan;
    private String linkWebsite;
    private String keterangan;
}
