package com.rony.oracleemployee.services;

import com.rony.oracleemployee.model.Country;
import com.rony.oracleemployee.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryService {

    @Autowired
    private final CountryRepository countryRepository;

    public void addCountry(Country country){
        countryRepository.save(country);
    }

    public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }

    public Country getById(Long id){
        return countryRepository.getById(id);
    }

    public void deleteCountry(long id){
        countryRepository.deleteById(id);
    }

    public Country getByCountryCode(String countryCode){
        return countryRepository.getCountryByCountryCode(countryCode);
    }


}
