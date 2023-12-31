package com.molis.molis.Service;


import com.molis.molis.DTO.MerkDto;
import com.molis.molis.DTO.MerkResponse;
import com.molis.molis.Model.Merk;

import java.util.List;

public interface MerkService {
    Merk createMerk(MerkDto merkDto);

    Merk updateMerk(Integer id, Merk updatedMerk);

    List<Merk> getAllMerk();

    Merk getMerkById(Integer id);


    void softDeleteById(Integer merkId);

    List<Merk> getActiveMerk();

    List<Merk> findByNamaPerusahaan(String name);

//    void deactivateMerk(Integer merkId);

    void toggleMerkStatus(Integer merkId);

    List<MerkResponse> findMerksBySearchTerm(String searchTerm);
}
