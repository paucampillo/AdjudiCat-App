package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adj_usuari")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuari", unique = true, nullable = false)
    private Long idUsuari;

    @Column(name = "nom", nullable = false, length = 128)
    private String nom;

    @Column(name = "email", unique = true, nullable = false, length = 128)
    private String email;

    @Column(name = "contrasenya", nullable = false, length = 64)
    private String contrasenya;

    @Column(name = "pais", length = 32)
    private String pais;

    @Column(name = "codi_postal")
    private Integer codiPostal;

    @Column(name = "direccio", length = 128)
    private String direccio;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "notificicacions_actives")
    private Boolean notificacionsActives;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "enllac_perfil_social")
    private String enllacPerfilSocial;

    @Column(name = "ident_nif", length = 128)
    private String identNif;

    @Column(name = "bloquejat")
    private Boolean bloquejat;

    @ManyToOne
    @JoinColumn(name = "id_idioma", nullable = false)
    private IdiomesEntity idioma;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RolsEntity rol;

    @ManyToOne
    @JoinColumn(name = "id_organ")
    private OrganEntity organ;
}