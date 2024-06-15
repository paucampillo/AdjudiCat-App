package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "adj_chatbot")
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo", nullable = false, unique = true)
    private Long idArticulo;

    @JoinColumn(name = "texto", nullable = false)
    private String texto;

}