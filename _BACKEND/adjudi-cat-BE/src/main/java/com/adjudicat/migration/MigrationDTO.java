package com.adjudicat.migration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MigrationDTO {
    public String codi_ambit;
    public String nom_ambit;
    public String codi_departament_ens;
    public String nom_departament_ens;
    public String codi_organ;
    public String nom_organ;
    public String codi_unitat;
    public String nom_unitat;
    public String codi_ine10;
    public String codi_dir3;
    public String codi_expedient;
    public String tipus_contracte;
    public String subtipus_contracte;
    public String procediment;
    public String fase_publicacio;
    public String denominacio;
    public String objecte_contracte;
    public String pressupost_licitacio;
    public String valor_estimat_contracte;
    public String codi_nuts;
    public String lloc_execucio;
    public String duracio_contracte;
    public Date termini_presentacio_ofertes;
    public Date data_publicacio_anunci;
    public String codi_cpv;
    public String enllac_publicacio;
    public String es_agregada;
    public Date data_publicacio_adjudicacio;
    public String ofertes_rebudes;
    public String resultat;
    public String tipus_identificacio;
    public String identificacio_adjudicatari;
    public String denominacio_adjudicatari;
    public String import_adjudicacio_sense;
    public String import_adjudicacio_amb_iva;
    public Date data_adjudicacio_contracte;
    public Date data_formalitzacio_contracte;
    public String numero_lot;
    public String descripcio_lot;
    public Date data_publicacio_previ;
    public Date data_publicacio_anul;
    public Date data_publicacio_futura;
    public Date data_publicacio_formalitzacio;
}
