package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Society;
import java.util.List;
import java.util.Optional;

public interface SocietyService {
    Society createSociety(Society society);
    List<Society> getAllSocieties();
    Optional<Society> getSocietyById(Long id);
    void deleteSociety(Long id);
    Optional<Society> getSocietyByName(String name);
    boolean checkSocietyExistByName(String name);

}

