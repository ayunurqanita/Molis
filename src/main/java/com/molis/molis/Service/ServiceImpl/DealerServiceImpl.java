package com.molis.molis.Service.ServiceImpl;

import com.molis.molis.DTO.DealerDto;
import com.molis.molis.DTO.DealerResponse;
import com.molis.molis.DTO.MerkResponse;
import com.molis.molis.Model.Dealer;
import com.molis.molis.Model.Merk;
import com.molis.molis.Repository.DealerRepository;
import com.molis.molis.Repository.MerkRepository;
import com.molis.molis.Service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DealerServiceImpl implements DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private MerkRepository merkRepository;

    @Override
    public Dealer createDealer(DealerDto dealerDto) {
        try {
            // Cek apakah Merk dengan ID tersebut ada
            Merk merk = merkRepository.findById(dealerDto.getMerkId())
                    .orElseThrow(() -> new EntityNotFoundException("Merk dengan ID " + dealerDto.getMerkId() + " tidak ditemukan"));

            // Buat dealer baru tanpa memeriksa apakah nama dealer sudah ada
            Dealer newDealer = new Dealer();
            newDealer.setNamaDealer(dealerDto.getNamaDealer());
            newDealer.setAlamat(dealerDto.getAlamat());
            newDealer.setKontak(dealerDto.getKontak());
            newDealer.setLinkWebsite(dealerDto.getLinkWebsite());
            newDealer.setMap(dealerDto.getMap());
            newDealer.setLatitude(dealerDto.getLatitude());
            newDealer.setLongitude(dealerDto.getLongitude());
            newDealer.setKeterangan(dealerDto.getKeterangan());
            newDealer.setMerkId(merk);
            newDealer.setCreatedBy(dealerDto.getCreatedBy());
            newDealer.setCreatedDate(LocalDateTime.now());

            return dealerRepository.save(newDealer);
        } catch (EntityNotFoundException e) {
            // Tangani pengecualian dan lemparkan kembali sebagai EntityNotFoundException
            throw e;
        } catch (Exception e) {
            // Tangani pengecualian umum dan lemparkan sebagai RuntimeException
            throw new RuntimeException("Terjadi kesalahan saat membuat dealer", e);
        }
    }


    @Override
    public Dealer updateDealer(Integer id, Dealer updatedDealer) {
        Dealer existingDealer = dealerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dealer not found with id: " + id));

        // Memperbarui propertinya dengan nilai yang diberikan
        existingDealer.setNamaDealer(updatedDealer.getNamaDealer());
        existingDealer.setAlamat(updatedDealer.getAlamat());
        existingDealer.setKontak(updatedDealer.getKontak());
        existingDealer.setLinkWebsite(updatedDealer.getLinkWebsite());
        existingDealer.setMap(updatedDealer.getMap());
        existingDealer.setLatitude(updatedDealer.getLatitude());
        existingDealer.setLongitude(updatedDealer.getLongitude());
        existingDealer.setKeterangan(updatedDealer.getKeterangan());
        existingDealer.setMerkId(updatedDealer.getMerkId());
        existingDealer.setUpdatedBy(updatedDealer.getUpdatedBy());
        existingDealer.setUpdatedDate(LocalDateTime.now());

        // Perbarui merk jika perlu
        if (updatedDealer.getMerkId() != null) {
            Merk existingMerk = merkRepository.findById(updatedDealer.getMerkId().getMerkId())
                    .orElseThrow(() -> new RuntimeException("Merk not found with id: " + updatedDealer.getMerkId().getMerkId()));

            existingDealer.setMerkId(existingMerk);
        }

        // Simpan perubahan ke dalam database
        return dealerRepository.save(existingDealer);
    }

    private DealerResponse convertToDealerResponse(Dealer dealer) {
        DealerResponse response = new DealerResponse();

        response.setDealerId(dealer.getDealerId());
        response.setNamaDealer(dealer.getNamaDealer());
        response.setAlamat(dealer.getAlamat());
        response.setKontak(dealer.getKontak());
        response.setLinkWebsite(dealer.getLinkWebsite());
        response.setMap(dealer.getMap());
        response.setLatitude(dealer.getLatitude());
        response.setLongitude(dealer.getLongitude());
        response.setActive(dealer.getActive());
        response.setMerk(dealer.getMerkId());
        response.setKeterangan(dealer.getKeterangan());

        // Pengecekan null untuk objek Merk
        if (dealer.getMerkId() != null) {
            MerkResponse merkResponse = new MerkResponse();
            Merk merk = dealer.getMerkId();
            merkResponse.setNamaMerk(merk.getNamaMerk());

            response.setNamaMerk(merkResponse.getNamaMerk());
        } else {
            // Atau, sesuaikan dengan kebutuhan Anda, misalnya, setNamaMerk menjadi null atau string kosong
            response.setNamaMerk(null);
        }

        return response;
    }


    @Override
    public List<DealerResponse> getAllDealer() {
        List<Dealer> dealers = dealerRepository.findAll();
        return dealers.stream()
                .map(this::convertToDealerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DealerResponse getDealerById(Integer dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new EntityNotFoundException("Dealer not found with id: " + dealerId));

        return convertToDealerResponse(dealer);
    }


//    @Override
//    public List<DealerResponse> findAllByName(String namaDealer) {
//        List<Dealer> dealers = dealerRepository.findAllByNamaDealer(namaDealer);
//
//        // Lakukan konversi ke DealerResponse atau manipulasi data lainnya sesuai kebutuhan
//        List<DealerResponse> responses = dealers.stream()
//                .map(this::convertToDealerResponse)
//                .collect(Collectors.toList());
//
//        return responses;
//    }


    @Override
    public void softDeleteById(Integer dealerId) {
        dealerRepository.softDeleteById(dealerId);
    }

    @Override
    public List<DealerResponse> getActiveDealer() {
        List<Dealer> dealers = dealerRepository.findByActiveTrueAndDeletedFalse();
        return dealers.stream()
                .map(this::convertToDealerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateDealer(Integer dealerId) {
        Optional<Dealer> dealerOptional = dealerRepository.findById(dealerId);

        if (dealerOptional.isPresent()) {
            Dealer dealer = dealerOptional.get();
            dealer.setActive(false); // Set active status to false
            dealerRepository.save(dealer);
        } else {
            // Dealer not found, handle accordingly
        }
    }

    @Override
    public List<DealerResponse> findActiveDealers(String namaDealer) {
        List<Dealer> dealers = dealerRepository.findByNamaDealerAndActiveTrueAndDeletedFalse(namaDealer);

        // Lakukan konversi ke DealerResponse atau manipulasi data lainnya sesuai kebutuhan
        List<DealerResponse> responses = dealers.stream()
                .map(this::convertToDealerResponse)
                .collect(Collectors.toList());

        return responses;
    }
}
