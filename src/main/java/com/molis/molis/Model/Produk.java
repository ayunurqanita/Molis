package com.molis.molis.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produk")
@SQLDelete(sql = "UPDATE produk SET deleted = true WHERE id=?")
@EntityListeners(AuditingEntityListener.class)
public class Produk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produk_id")
    private Integer produkId;

    @Column(name = "nama_produk")
    private String namaProduk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "merk_id", referencedColumnName = "merk_id")
    private Merk merkId;

    @Column(name = "estimasi_jarak")
    private String estimasiJarak;

    @Column(name = "kecepatan_max")
    private String kecepatanMax;

    @Column(name = "kapasitas_baterai")
    private String kapasitasBaterai;

    @Column(name = "ketahanan_baterai")
    private String ketahananBaterai;

    @Column(name = "daya_max")
    private String dayaMax;

    @Column(name = "link_website_produk", length = 1000)
    private String linkWebsiteProduk;

    @Column(name = "logo")
    private String logo;

    @Column(name = "gambar_produk")
    private String gambarProduk;

    @Column(name ="keterangan", length = 1000)
    private String keterangan;

    @JsonIgnore
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JsonIgnore
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @JsonIgnore
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @JsonIgnore
    @Column(name = "is_active")
    private boolean active = Boolean.TRUE;

    @JsonIgnore
    @Column(name = "is_deleted")
    private boolean deleted = Boolean.FALSE;


    public Boolean getActive() {
        return active;
    }

    public boolean isActive() {
        return active;
    }
}
