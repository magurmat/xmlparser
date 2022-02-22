package com.demo.xmlparser.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Municipality {

    @Id
    private String code;

    private String name;
}
