package com.molis.molis.Controller;

import com.molis.molis.DTO.MerkDto;
import com.molis.molis.Model.Merk;
import com.molis.molis.Service.MerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/merk")
public class MerkController {

    private final MerkService merkService;

    @Autowired
    public MerkController(MerkService merkService) {
        this.merkService = merkService;
    }

    @PostMapping("/add")
    public ResponseEntity<Merk> createMerk(@RequestBody MerkDto merkDto) {
        Merk createdMerk = merkService.createMerk(merkDto);
        return ResponseEntity.ok(createdMerk);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Merk> updateMerk(@PathVariable Integer id, @RequestBody Merk updatedMerk) {
        try {
            Merk result = merkService.updateMerk(id, updatedMerk);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public List<Merk> getAllMerks() {
        return merkService.getAllMerk();
    }

    @GetMapping("/{id}")
    public Merk getMerkById(@PathVariable Integer id) {
        return merkService.getMerkById(id);
    }

    @GetMapping("/findByName")
    public List<Merk> findByName(@RequestParam String namaMerk) {
        return merkService.findByName(namaMerk);
    }

    @GetMapping("/findByNamaPerusahaan")
    public List<Merk> findByNamaPerusahaan(@RequestParam String namaPerusahaan) { return merkService.findByNamaPerusahaan(namaPerusahaan);}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMerk(@PathVariable("id") Integer merkId) {
        merkService.softDeleteById(merkId);
        return ResponseEntity.ok("Merk with ID " + merkId + " soft deleted successfully.");
    }

    @GetMapping("/active")
    public List<Merk> getActiveMerk() { return merkService.getActiveMerk();}

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateMerk(@PathVariable Integer id) {
        merkService.deactivateMerk(id);
        return ResponseEntity.ok("Deactivation successful");
    }
}
