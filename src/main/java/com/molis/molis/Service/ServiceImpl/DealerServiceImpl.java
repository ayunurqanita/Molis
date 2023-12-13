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
import org.springframework.data.crossstore.ChangeSetPersister;
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
public class DealerServiceImpl implements DealerService {

    @Autowired
    public MerkRepository merkRepository;

    @Autowired
    public DealerRepository dealerRepository;

    @Override
    public DealerResponse createDealer(DealerDto dealerDto) {
        try {
            // Cek apakah Merk dengan ID tersebut ada
            Merk merk = merkRepository.findById(dealerDto.getMerkId())
                    .orElseThrow(() -> new EntityNotFoundException("Merk dengan ID " + dealerDto.getMerkId() + " tidak ditemukan"));

            // Cek apakah dealer dengan nama tersebut sudah ada
            boolean existingDealer = dealerRepository.existsByNamaDealer(dealerDto.getNamaDealer());

            if (!existingDealer) {
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
                newDealer.setActive(true);
                newDealer.setDeleted(false);

                Dealer savedDealer = dealerRepository.save(newDealer);

                // Membuat objek DealerResponse
                DealerResponse response = new DealerResponse();
                response.setDealerId(savedDealer.getDealerId());
                response.setNamaDealer(savedDealer.getNamaDealer());
                response.setAlamat(savedDealer.getAlamat());
                response.setKontak(savedDealer.getKontak());
                response.setLinkWebsite(savedDealer.getLinkWebsite());
                response.setMap(savedDealer.getMap());
                response.setLatitude(savedDealer.getLatitude());
                response.setLongitude(savedDealer.getLongitude());
                response.setKeterangan(savedDealer.getKeterangan());
                response.setMerk(merk);

                return response;
            } else {
                // Handle existing dealer, misalnya dengan memberikan respons khusus
                throw new EntityExistsException("Dealer dengan nama tersebut sudah ada");
            }
        } catch (EntityNotFoundException e) {
            // Tangani pengecualian dan lemparkan kembali sebagai EntityNotFoundException
            throw e;
        } catch (EntityExistsException e) {
            // Tangani pengecualian dan lemparkan kembali sebagai EntityExistsException
            throw e;
        } catch (Exception e) {
            // Tangani pengecualian umum dan lemparkan sebagai RuntimeException
            throw new RuntimeException("Terjadi kesalahan saat membuat dealer", e);
        }
    }


    @Override
    public Dealer updateDealer(Integer id, Dealer updatedDealer) {
        // Memastikan bahwa dealer dengan ID yang diberikan ada dalam database
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
        existingDealer.setMerkId(updatedDealer.getMerkId());
        existingDealer.setKeterangan(updatedDealer.getKeterangan());
        existingDealer.setUpdatedBy(updatedDealer.getUpdatedBy());
        existingDealer.setUpdatedDate(LocalDateTime.now());
        existingDealer.setActive(updatedDealer.isActive());
        existingDealer.setDeleted(updatedDealer.isDeleted());// Pastikan bahwa nilai active diatur dengan benar

        // Simpan perubahan ke dalam database
        return dealerRepository.save(existingDealer);
    }

    @Override
    public List<DealerResponse> getAllDealers() {
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


    private DealerResponse convertToDealerResponse(Dealer dealer) {
        DealerResponse response = new DealerResponse();

        response.setDealerId(dealer.getDealerId());
        response.setNamaDealer(dealer.getNamaDealer());
        response.setDealerId(dealer.getDealerId());
        response.setNamaDealer(dealer.getNamaDealer());
        response.setAlamat(dealer.getAlamat());
        response.setKontak(dealer.getKontak());
        response.setLinkWebsite(dealer.getLinkWebsite());
        response.setMap(dealer.getMap());
        response.setLatitude(dealer.getLatitude());
        response.setLongitude(dealer.getLongitude());
        response.setKeterangan(dealer.getKeterangan());
        response.setMerk(dealer.getMerkId());


        MerkResponse merkResponse = new MerkResponse();
        Merk merk = dealer.getMerkId();
        merkResponse.setNamaMerk(merk.getNamaMerk());

        return response;
    }

    @Override
    public DealerResponse findByName(String namaDealer) {
        // implementasi untuk mencari dealer berdasarkan nama
        Dealer dealer = dealerRepository.findByNamaDealer(namaDealer);

        if (dealer != null) {
            return convertToDealerResponse(dealer);
        } else {
            // handle jika dealer tidak ditemukan
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dealer dengan nama " + namaDealer + " tidak ditemukan");
        }
    }

    @Override
    public void softDeleteById(Integer dealerId) {
        dealerRepository.softDeleteById(dealerId);
    }

    @Override
    public List<DealerResponse> getActiveDealers() {
        List<Dealer> dealers = dealerRepository.findByActiveTrueAndDeletedFalse();
        return dealers.stream()
                .map(this::convertToDealerResponse)
                .collect(Collectors.toList());
    }

}
