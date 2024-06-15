package com.adjudicat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganDTO {
    private Long idOrgan;
    private Long codi;
    private String nom;
    private DepartamentDTO departament;
}