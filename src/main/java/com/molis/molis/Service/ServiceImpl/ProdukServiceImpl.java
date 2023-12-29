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
import org.springframework.stereotype.Service;

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

            return produkRepository.save(newProduk);
        } catch (EntityNotFoundException e) {
            // Tangani pengecualian dan lemparkan kembali sebagai EntityNotFoundException
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

        // Perbarui merk jika perlu
        if (updatedProduk.getMerkId() != null) {
            Merk existingMerk = merkRepository.findById(updatedProduk.getMerkId().getMerkId())
                    .orElseThrow(() -> new RuntimeException("Merk not found with id: " + updatedProduk.getMerkId().getMerkId()));

            existingProduk.setMerkId(existingMerk);
        }

        // Simpan perubahan ke dalam database
        return produkRepository.save(existingProduk);
    }

    private ProdukResponse convertToProdukResponse(Produk produk) {
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
        response.setActive(produk.getActive());

        // Pengecekan null untuk objek Merk
        if (produk.getMerkId()!= null) {
            MerkResponse merkResponse = new MerkResponse();
            Merk merk = produk.getMerkId();
            merkResponse.setNamaMerk(merk.getNamaMerk());

            response.setMerk(merk);
        } else {
            // Atau, sesuaikan dengan kebutuhan Anda, misalnya, setMerk menjadi null atau objek MerkResponse default
            response.setMerk((Merk) null);
        }

        return response;
    }


    @Override
    public List<ProdukResponse> getAllProduk() {
        List<Produk> produks = produkRepository.findAll();
        return produks.stream()
                .map(this::convertToProdukResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProdukResponse getProdukById(Integer produkId) {
        Produk produk = produkRepository.findById(produkId)
                .orElseThrow(() -> new EntityNotFoundException("Produk not found with id: " + produkId));

        return convertToProdukResponse(produk);
    }

    @Override
    public List<ProdukResponse> findActiveProduksByName(String namaProduk) {
        List<Produk> produks = produkRepository.findActiveProduksByName(namaProduk);

        // Konversi ke ProdukResponse atau lakukan manipulasi lain sesuai kebutuhan
        return produks.stream()
                .map(this::convertToProdukResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteById(Integer produkId){ produkRepository.softDeleteById(produkId); }

    @Override
    public List<ProdukResponse> getActiveProduk() {
        List<Produk> produks = produkRepository.findByActiveTrueAndDeletedFalseOrActiveFalseAndDeletedFalse();
        return produks.stream()
                .map(this::convertToProdukResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateProduk(Integer produkId) {
        Optional<Produk> produkOptional = produkRepository.findById(produkId);

        if (produkOptional.isPresent()) {
            Produk produk = produkOptional.get();
            produk.setActive(false); // Set active status to false
            produkRepository.save(produk);
        } else {
            // Product not found, handle accordingly
        }
    }
}

