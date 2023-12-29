package com.molis.molis.Model;

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
@Table(name = "merk")
@SQLDelete(sql = "UPDATE merk SET deleted = true WHERE id=?")
@EntityListeners(AuditingEntityListener.class)
public class Merk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merk_id")
    private Integer merkId;

    @Column(name = "nama_merk")
    private String namaMerk;

    @Column(name = "nama_perusahaan")
    private String namaPerusahaan;

    @Column(name = "link_website", length = 1000)
    private String linkWebsite;

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

    @Column(name = "is_active")
    private boolean active = Boolean.TRUE;

    @JsonIgnore
    @Column(name = "is_deleted")
    private boolean deleted = Boolean.FALSE;

    public Merk(Integer merkId) {
        this.merkId = merkId;
    }


    public Boolean getActive() {
        return active;
    }
}

