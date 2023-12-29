package com.molis.molis.Service;


import com.molis.molis.DTO.DealerDto;
import com.molis.molis.DTO.DealerResponse;
import com.molis.molis.Model.Dealer;

import java.util.List;

public interface DealerService {

    Dealer createDealer(DealerDto dealerDto);

    Dealer updateDealer(Integer id, Dealer updatedDealer);

    DealerResponse getDealerById(Integer id);

    void softDeleteById(Integer dealerId);

    List<DealerResponse> getActiveDealer();

    List<DealerResponse> getAllDealer();

    void deactivateDealer(Integer dealerId);

//    List<DealerResponse> findActiveDealers(String namaDealer);

    List<DealerResponse> findActiveDealersByName(String namaDealer);
}
