package com.molis.molis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MerkDto {
    private String namaMerk;
    private String namaPerusahaan;
    private String linkWebsite;
    private String keterangan;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private boolean active;
    private boolean deleted;

}
