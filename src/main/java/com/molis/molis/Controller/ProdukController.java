package com.molis.molis.Controller;

import com.molis.molis.DTO.ProdukDto;
import com.molis.molis.DTO.ProdukResponse;
import com.molis.molis.Model.Merk;
import com.molis.molis.Model.Produk;
import com.molis.molis.Repository.MerkRepository;
import com.molis.molis.Service.ProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/produk")
public class ProdukController {

    @Autowired
    public ProdukService produkService;

    @Autowired
    public MerkRepository merkRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProdukResponse> createProduk(@RequestBody ProdukDto produkDto) {
        try {
            Produk createdProduk = produkService.createProduk(produkDto);

            if (createdProduk != null) {
                // Optional: Load additional data or perform additional actions if needed
                // For example, load Merk information associated with the created Produk
                Merk merk = merkRepository.findById(produkDto.getMerkId())
                        .orElseThrow(() -> new EntityNotFoundException("Merk dengan ID " + produkDto.getMerkId() + " tidak ditemukan"));

                // Create ProdukResponse object
                ProdukResponse response = new ProdukResponse();
                response.setProdukId(createdProduk.getProdukId());
                response.setNamaProduk(createdProduk.getNamaProduk());
                response.setEstimasiJarak(createdProduk.getEstimasiJarak());
                response.setKecepatanMax(createdProduk.getKecepatanMax());
                response.setKapasitasBaterai(createdProduk.getKapasitasBaterai());
                response.setKetahananBaterai(createdProduk.getKetahananBaterai());
                response.setDayaMax(createdProduk.getDayaMax());
                response.setLinkWebsiteProduk(createdProduk.getLinkWebsiteProduk());
                response.setLogo(createdProduk.getLogo());
                response.setGambarProduk(createdProduk.getGambarProduk());
                response.setKeterangan(createdProduk.getKeterangan());
                response.setMerk(merk);

                return ResponseEntity.ok(response);
            } else {
                // Handle case when createdProduk is null
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (EntityNotFoundException e) {
            // Handle EntityNotFoundException, for example, return a 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (EntityExistsException e) {
            // Handle EntityExistsException, for example, return a 409 response
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            // Handle other exceptions, for example, return a 500 response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProdukResponse> updateProduk(@PathVariable Integer id, @RequestBody Produk updatedProdukDto) {
        try {
            Produk updatedProduk = produkService.updateProduk(id, updatedProdukDto);

            if (updatedProduk != null) {
                // Optional: Load additional data or perform additional actions if needed
                // For example, load Merk information associated with the updated Produk
                Merk merk = merkRepository.findById(updatedProdukDto.getMerkId().getMerkId())
                        .orElseThrow(() -> new EntityNotFoundException("Merk dengan ID " + updatedProdukDto.getMerkId() + " tidak ditemukan"));

                // Create ProdukResponse object
                ProdukResponse response = new ProdukResponse();
                response.setProdukId(updatedProduk.getProdukId());
                response.setNamaProduk(updatedProduk.getNamaProduk());
                response.setEstimasiJarak(updatedProduk.getEstimasiJarak());
                response.setKecepatanMax(updatedProduk.getKecepatanMax());
                response.setKapasitasBaterai(updatedProduk.getKapasitasBaterai());
                response.setKetahananBaterai(updatedProduk.getKetahananBaterai());
                response.setDayaMax(updatedProduk.getDayaMax());
                response.setLinkWebsiteProduk(updatedProduk.getLinkWebsiteProduk());
                response.setLogo(updatedProduk.getLogo());
                response.setGambarProduk(updatedProduk.getGambarProduk());
                response.setKeterangan(updatedProduk.getKeterangan());
                response.setMerk(merk);

                return ResponseEntity.ok(response);
            } else {
                // Handle case when updatedProduk is null
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (EntityNotFoundException e) {
            // Handle EntityNotFoundException, for example, return a 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Handle other exceptions, for example, return a 500 response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<ProdukResponse>> getAllProduk() {
        List<ProdukResponse> produkList = produkService.getAllProduk();
        return new ResponseEntity<>(produkList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdukResponse> getProdukById(@PathVariable Integer id) {
        ProdukResponse produk = produkService.getProdukById(id);
        return new ResponseEntity<>(produk, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ProdukResponse findByName(@RequestParam String namaProduk) {
        return produkService.findByName(namaProduk);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProduk(@PathVariable("id") Integer produkId) {
        produkService.softDeleteById(produkId);
        return ResponseEntity.ok("Produk with ID " + produkId + " soft deleted successfully.");
    }

    @GetMapping("/active")
    public List<ProdukResponse> getActiveProduk() { return produkService.getActiveProduk();}
}
