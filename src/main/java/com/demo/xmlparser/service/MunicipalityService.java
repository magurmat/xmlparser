package com.demo.xmlparser.service;

import com.demo.xmlparser.dto.MunicipalityDTO;
import com.demo.xmlparser.entity.Municipality;
import com.demo.xmlparser.repository.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    @Autowired
    public MunicipalityService(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }

    public Optional<Municipality> findByCode(String code) {
        return municipalityRepository.findById(code);
    }

    public MunicipalityDTO create(MunicipalityDTO municipalityDTO) {
        return toDTO(municipalityRepository.save(
                new Municipality(
                        municipalityDTO.getCode(),
                        municipalityDTO.getName()
                )
        ));
    }

    private MunicipalityDTO toDTO(Municipality municipality) {
        return new MunicipalityDTO(municipality.getCode(), municipality.getName());
    }
}
