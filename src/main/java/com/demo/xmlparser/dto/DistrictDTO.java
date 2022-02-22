package com.demo.xmlparser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DistrictDTO {
    private final String code;
    private final String name;
    private final String municipalityCode;
}
