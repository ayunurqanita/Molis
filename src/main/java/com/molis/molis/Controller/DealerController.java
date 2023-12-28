package com.molis.molis.Controller;

import com.molis.molis.DTO.DealerDto;
import com.molis.molis.DTO.DealerResponse;
import com.molis.molis.Model.Dealer;
import com.molis.molis.Model.Merk;
import com.molis.molis.Repository.MerkRepository;
import com.molis.molis.Service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dealers")
public class DealerController {

    @Autowired
    public DealerService dealerService;

    @Autowired
    public MerkRepository merkRepository;

    @PostMapping("/add")
    public ResponseEntity<DealerResponse> createDealer(@RequestBody DealerDto dealerDto) {
        try {
            Dealer createdDealer = dealerService.createDealer(dealerDto);

            if (createdDealer != null) {
                // Optional: Load additional data or perform additional actions if needed
                // For example, load Merk information associated with the created Dealer
                Merk merk = merkRepository.findById(dealerDto.getMerkId())
                        .orElseThrow(() -> new EntityNotFoundException("Merk dengan ID " + dealerDto.getMerkId() + " tidak ditemukan"));

                // Create DealerResponse object
                DealerResponse response = new DealerResponse();
                response.setDealerId(createdDealer.getDealerId());
                response.setNamaDealer(createdDealer.getNamaDealer());
                response.setAlamat(createdDealer.getAlamat());
                response.setKontak(createdDealer.getKontak());
                response.setLinkWebsite(createdDealer.getLinkWebsite());
                response.setMap(createdDealer.getMap());
                response.setLatitude(createdDealer.getLatitude());
                response.setLongitude(createdDealer.getLongitude());
                response.setKeterangan(createdDealer.getKeterangan());
                response.setActive(createdDealer.getActive());
                response.setMerk(merk);

                return ResponseEntity.ok(response);
            } else {
                // Handle case when createdDealer is null
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
                response.setActive(updatedDealer.getActive());
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
    public ResponseEntity<List<DealerResponse>> getAllDealer() {
        List<DealerResponse> dealerList = dealerService.getAllDealer();
        return new ResponseEntity<>(dealerList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerResponse> getDealerById(@PathVariable Integer id) {
        DealerResponse dealer = dealerService.getDealerById(id);
        return new ResponseEntity<>(dealer, HttpStatus.OK);
    }

//    @GetMapping("/findByName")
//    public ResponseEntity<List<DealerResponse>> findByName(@RequestParam String namaDealer) {
//        List<DealerResponse> dealers = dealerService.findAllByName(namaDealer);
//
//        if (dealers.isEmpty()) {
//            // Handle jika tidak ada dealer yang ditemukan
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            // Mengembalikan daftar dealer yang ditemukan
//            return new ResponseEntity<>(dealers, HttpStatus.OK);
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDealer(@PathVariable("id") Integer dealerId) {
        dealerService.softDeleteById(dealerId);
        return ResponseEntity.ok("Dealer with ID " + dealerId + " soft deleted successfully.");
    }

    @GetMapping("/active")
    public List<DealerResponse> getActiveDealer() {
        return dealerService.getActiveDealer();
    }

    @PutMapping("/{dealerId}/deactivate")
    public ResponseEntity<String> deactivateDealer(@PathVariable Integer dealerId) {
        dealerService.deactivateDealer(dealerId);
        return ResponseEntity.ok("Deactivation successful");
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<DealerResponse>> searchDealers(@RequestParam String namaDealer) {
        List<DealerResponse> dealers = dealerService.findActiveDealers(namaDealer);
        return ResponseEntity.ok(dealers);
    }
}
