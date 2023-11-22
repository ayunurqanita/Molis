package com.molis.molis.Controller;


import com.molis.molis.DTO.DealerDto;
import com.molis.molis.DTO.DealerResponse;
import com.molis.molis.Model.Dealer;
import com.molis.molis.Model.Merk;
import com.molis.molis.Repository.DealerRepository;
import com.molis.molis.Repository.MerkRepository;
import com.molis.molis.Service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/dealers")
public class DealerController {

    @Autowired
    public DealerService dealerService;

    @Autowired
    public DealerRepository dealerRepository;

    @Autowired
    public MerkRepository merkRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DealerResponse> createDealer(@RequestBody DealerDto dealerDto) {
        DealerResponse createdDealer = dealerService.createDealer(dealerDto);

        if (createdDealer != null) {
            Merk merk = merkRepository.findById(dealerDto.getMerkId())
                    .orElseThrow(() -> new EntityNotFoundException("Merk dengan ID " + dealerDto.getMerkId() + " tidak ditemukan"));

            createdDealer.setMerk(merk);

            return ResponseEntity.ok(createdDealer);
        } else {
            // Handle case when createdDealer is null

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DealerResponse> updateDealer(@PathVariable Integer id, @RequestBody Dealer updatedDealerDto) {
        try {
            Dealer updatedDealer = dealerService.updateDealer(id, updatedDealerDto);

            if (updatedDealer != null) {
                // Optional: Load additional data or perform additional actions if needed
                // For example, load Merk information associated with the updated Dealer
                Merk merk = merkRepository.findById(updatedDealerDto.getMerkId().getMerkId())
                        .orElseThrow(() -> new EntityNotFoundException("Merk dengan ID " + updatedDealerDto.getMerkId() + " tidak ditemukan"));

                // Create DealerResponse object
                DealerResponse response = new DealerResponse();
                response.setDealerId(updatedDealer.getDealerId());
                response.setNamaDealer(updatedDealer.getNamaDealer());
                response.setAlamat(updatedDealer.getAlamat());
                response.setKontak(updatedDealer.getKontak());
                response.setLinkWebsite(updatedDealer.getLinkWebsite());
                response.setMap(updatedDealer.getMap());
                response.setLatitude(updatedDealer.getLatitude());
                response.setLongitude(updatedDealer.getLongitude());
                response.setKeterangan(updatedDealer.getKeterangan());
                response.setMerk(merk);

                return ResponseEntity.ok(response);
            } else {
                // Handle case when updatedDealer is null
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
    public List<DealerResponse> getAllDealers() {
        return dealerService.getAllDealers();
    }

    @GetMapping("/{id}")
    public DealerResponse getDealerById(@PathVariable Integer id) {
        return dealerService.getDealerById(id);
    }

    @GetMapping("/findByName")
    public DealerResponse findByName(@RequestParam String namaDealer) {
        return dealerService.findByName(namaDealer);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteDealer(@PathVariable("id") Integer dealerId) {
        dealerService.softDeleteById(dealerId);
        return ResponseEntity.ok("Dealer with ID " + dealerId + " soft deleted successfully.");
    }

    @GetMapping("/active")
    public List<DealerResponse> getActiveDealers() { return dealerService.getActiveDealers();}
}
