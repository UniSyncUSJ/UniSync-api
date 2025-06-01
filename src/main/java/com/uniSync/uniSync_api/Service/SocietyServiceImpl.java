package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Society;
import com.uniSync.uniSync_api.Repository.SocietyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocietyServiceImpl implements SocietyService {

    private final SocietyRepository societyRepository;

    @Autowired
    public SocietyServiceImpl(SocietyRepository societyRepository) {
        this.societyRepository = societyRepository;
    }

    @Override
    public Society createSociety(Society society) {
        return societyRepository.save(society);
    }

    @Override
    public List<Society> getAllSocieties() {
        return societyRepository.findAll();
    }

    @Override
    public Optional<Society> getSocietyById(Long id) {
        return societyRepository.findById(id);
    }

    @Override
    public void deleteSociety(Long id) {
        societyRepository.deleteById(id);
    }

    @Override
    public Optional<Society> getSocietyByName(String name) {
        return societyRepository.findByName(name);
    }

    @Override
    public boolean checkSocietyExistByName(String name) {
        return societyRepository.existsByName(name);
    }
}

