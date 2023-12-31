package com.molis.molis.Service.ServiceImpl;

import com.molis.molis.DTO.MerkDto;
import com.molis.molis.DTO.MerkResponse;
import com.molis.molis.Model.Merk;
import com.molis.molis.Repository.MerkRepository;
import com.molis.molis.Service.MerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MerkServiceImpl implements MerkService {

    @Autowired
    private final MerkRepository merkRepository;

    public MerkServiceImpl(MerkRepository merkRepository) {
        this.merkRepository = merkRepository;
    }


    @Override
    public Merk createMerk(MerkDto merkDto) {
        try {
            // Cek apakah merk dengan nama tersebut sudah ada
            boolean existingMerk = merkRepository.existsByNamaMerk(merkDto.getNamaMerk());

            if (!existingMerk) {
                Merk newMerk = new Merk();
                newMerk.setNamaMerk(merkDto.getNamaMerk());
                newMerk.setNamaPerusahaan(merkDto.getNamaPerusahaan());
                newMerk.setLinkWebsite(merkDto.getLinkWebsite());
                newMerk.setKeterangan(merkDto.getKeterangan());
                newMerk.setCreatedBy(merkDto.getCreatedBy());
                newMerk.setCreatedDate(LocalDateTime.now());
                newMerk.setActive(true);
                newMerk.setDeleted(false);

                return merkRepository.save(newMerk);
            } else {
                // Handle existing merk, misalnya dengan memberikan respons khusus
                throw new EntityExistsException("Merk dengan nama tersebut sudah ada");
            }
        } catch (EntityExistsException e) {
            // Tangani pengecualian dan lemparkan kembali sebagai EntityExistsException
            throw e;
        } catch (Exception e) {
            // Tangani pengecualian umum dan lemparkan sebagai RuntimeException
            throw new RuntimeException("Terjadi kesalahan saat membuat merk", e);
        }
    }


    //Bisa Create Merk dengan nama yang sudah ada

//    @Override
//    public Merk createMerk(MerkDto merkDto) {
//        try {
//            Merk newMerk = new Merk();
//            newMerk.setNamaMerk(merkDto.getNamaMerk());
//            newMerk.setNamaPerusahaan(merkDto.getNamaPerusahaan());
//            newMerk.setLinkWebsite(merkDto.getLinkWebsite());
//            newMerk.setKeterangan(merkDto.getKeterangan());
//            newMerk.setCreatedBy(merkDto.getCreatedBy());
//            newMerk.setCreatedDate(LocalDateTime.now());
//            newMerk.setActive(true);
//            newMerk.setDeleted(false);
//
//            return merkRepository.save(newMerk);
//        } catch (Exception e) {
//            throw new RuntimeException("Terjadi kesalahan saat membuat merk", e);
//        }
//    }



    @Override
    public Merk updateMerk(Integer id, Merk updatedMerk) {
        // Memastikan bahwa merk dengan ID yang diberikan ada dalam database
        Merk existingMerk = merkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merk not found with id: " + id));

        // Memperbarui propertinya dengan nilai yang diberikan
        existingMerk.setNamaMerk(updatedMerk.getNamaMerk());
        existingMerk.setNamaPerusahaan(updatedMerk.getNamaPerusahaan());
        existingMerk.setLinkWebsite(updatedMerk.getLinkWebsite());
        existingMerk.setKeterangan(updatedMerk.getKeterangan());
        existingMerk.setUpdatedBy(updatedMerk.getUpdatedBy());
        existingMerk.setUpdatedDate(LocalDateTime.now());
        existingMerk.setActive(true);
        existingMerk.setDeleted(false);

        // Simpan perubahan ke dalam database
        return merkRepository.save(existingMerk);
    }


    @Override
    public List<Merk> getAllMerk() {
        return merkRepository.findAll();
    }

    @Override
    public Merk getMerkById(Integer id) {
        Optional<Merk> merk = merkRepository.findById(id);
        if (merk.isPresent()) {
            return merk.get();
        } else {
            // Handle error jika merk tidak ditemukan
            throw new RuntimeException("Merk not found with ID: " + id);
        }
    }

//    @Override
//    public List<Merk> findByName(String name) {
//        return merkRepository.findByNamaMerk(name);
//    }

    @Override
    public void softDeleteById(Integer merkId) {
        merkRepository.softDeleteById(merkId);
    }

    @Override
    public List<Merk> getActiveMerk() {
        return merkRepository.findByActiveTrueAndDeletedFalseOrActiveFalseAndDeletedFalse();
    }

    @Override
    public List<Merk> findByNamaPerusahaan(String name) { return merkRepository.findByNamaPerusahaan(name);}


    @Override
    public void toggleMerkStatus(Integer merkId) {
        Optional<Merk> merkOptional = merkRepository.findById(merkId);

        merkOptional.ifPresent(merk -> {
            merk.setActive(!merk.isActive()); // Toggle active status
            merkRepository.save(merk);
        });
    }

    private MerkResponse convertToMerkResponse(Merk merk) {
        MerkResponse response = new MerkResponse();

        response.setMerkId(merk.getMerkId());
        response.setNamaMerk(merk.getNamaMerk());
        response.setNamaPerusahaan(merk.getNamaPerusahaan());
        response.setLinkWebsite(merk.getLinkWebsite());
        response.setKeterangan(merk.getKeterangan());
        response.setActive(merk.getActive());
        return response;
    }

    @Override
    public List<MerkResponse> findMerksBySearchTerm(String searchTerm) {
        List<Merk> merks = merkRepository.findMerksBySearchTerm(searchTerm);

        // Lakukan konversi ke MerkResponse atau manipulasi data lainnya sesuai kebutuhan
        List<MerkResponse> responses = merks.stream()
                .map(this::convertToMerkResponse)
                .collect(Collectors.toList());

        return responses;
    }
}
