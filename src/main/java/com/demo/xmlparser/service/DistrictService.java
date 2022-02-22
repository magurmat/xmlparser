package com.demo.xmlparser.service;

import com.demo.xmlparser.dto.DistrictDTO;
import com.demo.xmlparser.entity.District;
import com.demo.xmlparser.entity.Municipality;
import com.demo.xmlparser.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final MunicipalityService municipalityService;

    @Autowired
    public DistrictService(DistrictRepository districtRepository, MunicipalityService municipalityService) {
        this.districtRepository = districtRepository;
        this.municipalityService = municipalityService;
    }

    public Optional<District> findByCode(String code) {
        return districtRepository.findById(code);
    }

    public DistrictDTO create(DistrictDTO districtDTO) throws Exception {
        Optional<Municipality> municipality = municipalityService.findByCode(districtDTO.getMunicipalityCode());
        if(municipality.isEmpty())
            throw new Exception("municipality not found!");
        return toDTO(districtRepository.save(
                new District(
                        districtDTO.getCode(),
                        districtDTO.getName(),
                        municipality.get()
                )
        ));
    }

    private DistrictDTO toDTO(District district) {
        return new DistrictDTO(district.getCode(), district.getName(), district.getMunicipality().getCode());
    }
}
