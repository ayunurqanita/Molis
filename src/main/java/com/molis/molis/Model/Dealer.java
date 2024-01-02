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
@Table(name = "dealer")
@SQLDelete(sql = "UPDATE dealer SET deleted = true WHERE id=?")
@EntityListeners(AuditingEntityListener.class)
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dealer_id")
    private Integer dealerId;

    @Column(name = "nama_dealer")
    private String namaDealer;

    @Column(name = "alamat", length = 500)
    private String alamat;

    @Column(name = "kontak")
    private String kontak;

    @Column(name = "link_website", length = 1000)
    private String linkWebsite;

    @Column(name = "link_maps", length = 1000)
    private String map;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "merk_id", referencedColumnName = "merk_id")
    @JsonBackReference
    private Merk merkId;


    @Column(name = "keterangan", length = 1000)
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
