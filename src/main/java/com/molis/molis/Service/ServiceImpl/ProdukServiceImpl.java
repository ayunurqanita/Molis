package com.molis.molis.Service.ServiceImpl;


import com.molis.molis.DTO.MerkResponse;
import com.molis.molis.DTO.ProdukDto;
import com.molis.molis.DTO.ProdukResponse;
import com.molis.molis.Model.Merk;
import com.molis.molis.Model.Produk;
import com.molis.molis.Repository.MerkRepository;
import com.molis.molis.Repository.ProdukRepository;
import com.molis.molis.Service.ProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdukServiceImpl implements ProdukService {

    @Autowired
    public ProdukRepository produkRepository;

    @Autowired
    public MerkRepository merkRepository;

    @Override
    public Produk createProduk(ProdukDto produkDto) {
        try {
            // Cek apakah Merk dengan ID tersebut ada
            Merk merk = merkRepository.findById(produkDto.getMerkId())
                    .orElseThrow(() -> new EntityNotFoundException("Merk dengan ID " + produkDto.getMerkId() + " tidak ditemukan"));

            // Cek apakah produk dengan nama tersebut sudah ada
            boolean existingProduk = produkRepository.existsByNamaProduk(produkDto.getNamaProduk());

            if (!existingProduk) {
                Produk newProduk = new Produk();
                newProduk.setNamaProduk(produkDto.getNamaProduk());
                newProduk.setMerkId(merk);
                newProduk.setEstimasiJarak(produkDto.getEstimasiJarak());
                newProduk.setKecepatanMax(produkDto.getKecepatanMax());
                newProduk.setKapasitasBaterai(produkDto.getKapasitasBaterai());
                newProduk.setKetahananBaterai(produkDto.getKetahananBaterai());
                newProduk.setDayaMax(produkDto.getDayaMax());
                newProduk.setLinkWebsiteProduk(produkDto.getLinkWebsiteProduk());
                newProduk.setLogo(produkDto.getLogo());
                newProduk.setGambarProduk(produkDto.getGambarProduk());
                newProduk.setKeterangan(produkDto.getKeterangan());
                newProduk.setCreatedBy(produkDto.getCreatedBy());
                newProduk.setCreatedDate(LocalDateTime.now());
                newProduk.setActive(true);
                newProduk.setDeleted(false);

                return produkRepository.save(newProduk);
            } else {
                // Handle existing produk, misalnya dengan memberikan respons khusus
                throw new EntityExistsException("Produk dengan nama tersebut sudah ada");
            }
        } catch (EntityNotFoundException e) {
            // Tangani pengecualian dan lemparkan kembali sebagai EntityNotFoundException
            throw e;
        } catch (EntityExistsException e) {
            // Tangani pengecualian dan lemparkan kembali sebagai EntityExistsException
            throw e;
        } catch (Exception e) {
            // Tangani pengecualian umum dan lemparkan sebagai RuntimeException
            throw new RuntimeException("Terjadi kesalahan saat membuat produk", e);
        }
    }

    @Override
    public Produk updateProduk(Integer id, Produk updatedProduk) {
        Produk existingProduk = produkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produk not found with id: " + id));

        // Memperbarui propertinya dengan nilai yang diberikan
        existingProduk.setNamaProduk(updatedProduk.getNamaProduk());
        existingProduk.setMerkId(updatedProduk.getMerkId());
        existingProduk.setEstimasiJarak(updatedProduk.getEstimasiJarak());
        existingProduk.setKecepatanMax(updatedProduk.getKecepatanMax());
        existingProduk.setKapasitasBaterai(updatedProduk.getKapasitasBaterai());
        existingProduk.setKetahananBaterai(updatedProduk.getKetahananBaterai());
        existingProduk.setDayaMax(updatedProduk.getDayaMax());
        existingProduk.setLinkWebsiteProduk(updatedProduk.getLinkWebsiteProduk());
        existingProduk.setLogo(updatedProduk.getLogo());
        existingProduk.setGambarProduk(updatedProduk.getGambarProduk());
        existingProduk.setKeterangan(updatedProduk.getKeterangan());
        existingProduk.setUpdatedBy(updatedProduk.getUpdatedBy());
        existingProduk.setUpdatedDate(LocalDateTime.now());
        existingProduk.setActive(true);
        existingProduk.setDeleted(false);

        // Perbarui merk jika perlu
        if (updatedProduk.getMerkId() != null) {
            Merk existingMerk = merkRepository.findById(updatedProduk.getMerkId().getMerkId())
                    .orElseThrow(() -> new RuntimeException("Merk not found with id: " + updatedProduk.getMerkId().getMerkId()));

            existingProduk.setMerkId(existingMerk);
        }

        // Simpan perubahan ke dalam database
        return produkRepository.save(existingProduk);
    }

    private ProdukResponse convertToDealerResponse(Produk produk){
        ProdukResponse response = new ProdukResponse();

        response.setProdukId(produk.getProdukId());
        response.setNamaProduk(produk.getNamaProduk());
        response.setMerk(produk.getMerkId());
        response.setEstimasiJarak(produk.getEstimasiJarak());
        response.setKecepatanMax(produk.getKecepatanMax());
        response.setKapasitasBaterai(produk.getKapasitasBaterai());
        response.setKetahananBaterai(produk.getKetahananBaterai());
        response.setDayaMax(produk.getDayaMax());
        response.setLinkWebsiteProduk(produk.getLinkWebsiteProduk());
        response.setLogo(produk.getLogo());
        response.setGambarProduk(produk.getGambarProduk());
        response.setKeterangan(produk.getKeterangan());

        MerkResponse merkResponse = new MerkResponse();
        Merk merk = produk.getMerkId();
        merkResponse.setNamaMerk(merk.getNamaMerk());

        return response;
    }

    @Override
    public List<ProdukResponse> getAllProduk() {
        List<Produk> produks = produkRepository.findAll();
        return produks.stream()
                .map(this::convertToDealerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProdukResponse getProdukById(Integer produkId) {
        Produk produk = produkRepository.findById(produkId)
                .orElseThrow(() -> new EntityNotFoundException("Produk not found with id: " + produkId));

        return convertToDealerResponse(produk);
    }

    @Override
    public ProdukResponse findByName(String namaProduk) {
        Produk produk = produkRepository.findByNamaProduk(namaProduk);

        if (produk != null) {
            return convertToDealerResponse(produk);
        } else {
            // handle jika dealer tidak ditemukan
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dealer dengan nama " + namaProduk + " tidak ditemukan");
        }
    }

    @Override
    public void softDeleteById(Integer produkId){ produkRepository.softDeleteById(produkId); }

    @Override
    public List<ProdukResponse> getActiveProduk() {
        List<Produk> produks = produkRepository.findByActiveTrueAndDeletedFalse();
        return produks.stream()
                .map(this::convertToDealerResponse)
                .collect(Collectors.toList());
    }
}

