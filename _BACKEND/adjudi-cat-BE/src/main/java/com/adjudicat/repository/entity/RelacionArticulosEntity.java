package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "adj_relacio_articles")
@AllArgsConstructor
@NoArgsConstructor
public class RelacionArticulosEntity {

    @Id
    @JoinColumn(name = "palabra", nullable = false)
    private String palabra;

    @ManyToOne
    @JoinColumn(name = "id_articulo", nullable = false)
    private ChatbotEntity article;

}