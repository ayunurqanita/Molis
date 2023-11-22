package com.molis.molis.Service;


import com.molis.molis.DTO.DealerDto;
import com.molis.molis.DTO.DealerResponse;
import com.molis.molis.Model.Dealer;

import java.util.List;

public interface DealerService {

    DealerResponse createDealer(DealerDto dealerDto);

    Dealer updateDealer(Integer id, Dealer updatedDealer);

    List<DealerResponse> getAllDealers();

    DealerResponse getDealerById(Integer id);

    DealerResponse findByName(String name);

    void softDeleteById(Integer dealerId);

    List<DealerResponse> getActiveDealers();
}
