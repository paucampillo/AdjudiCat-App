package com.adjudicat.controller;

import com.adjudicat.configuration.BaseTest;
import com.adjudicat.controller.dto.*;
import com.adjudicat.helper.DTOHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@AutoConfigureMockMvc
@Sql({
        "/db/sql/instanciar_tablas.sql",
        "/db/sql/llenar_datos.sql"
})
public class ContracteControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/v1/adj/contracte";
    private static final String PAGINCACIO = BASE + "/paginacio";
    private static final String HISTORIC = BASE + "/historic";
    private static final String PUBLICAR = BASE + "/publicar";
    private static final String CODI_CONTRACTE = BASE + "/findContracteCodi";
    private static final String HISTORIC_CREADOR = BASE + "/historicByCreador";
    private static final String CREADOR = BASE + "/byCreador";

    @Test
    public void paginacio_ok() throws Exception {
        FiltreDTO filtreDTO = FiltreDTO.builder()
                .codiExpedient("9").build();
        this.mockMvc.perform(post(PAGINCACIO)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("idUsuari", "3")
                .content(new ObjectMapper().writeValueAsString(filtreDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void historic_ok() throws Exception {
        FiltreDTO filtreDTO = FiltreDTO.builder()
                .codiExpedient("1").build();
        this.mockMvc.perform(post(HISTORIC)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("idUsuari", "3")
                .content(new ObjectMapper().writeValueAsString(filtreDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void publicar_ok() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2029-01-01");
        ContracteDTO contracteDTO = ContracteDTO.builder()
                .codiExpedient("10")
                .ambit(DTOHelper.getAmbitDTO())
                .lot(DTOHelper.getLotDTO())
                .usuariCreacio(DTOHelper.getUsuariPublicDTO())
                .terminiPresentacioOfertes(date)
                .build();
        this.mockMvc.perform(post(PUBLICAR)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(contracteDTO))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void publicar_ko() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2020-01-01");
        ContracteDTO contracteDTO = ContracteDTO.builder()
                .codiExpedient("1")
                .ambit(DTOHelper.getAmbitDTO())
                .lot(DTOHelper.getLotDTO())
                .usuariCreacio(DTOHelper.getUsuariPublicDTO())
                .terminiPresentacioOfertes(date)
                .build();
        this.mockMvc.perform(post(PUBLICAR)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(contracteDTO))
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteContracte_ok() throws Exception {
        this.mockMvc.perform(delete(BASE + "/9"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByCodiExpedient_ok() throws Exception {
        this.mockMvc.perform(get(CODI_CONTRACTE)
                        .param("codiExpedient", "1")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void findByCodiExpedient_ko() throws Exception {
        this.mockMvc.perform(get(CODI_CONTRACTE)
                .param("codiExpedient", "48754")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdContracte_ok() throws Exception {
        this.mockMvc.perform(get(BASE + "/1" + "/3"))
                .andExpect(status().isOk());
    }

    @Test
    public void historic_creador_ok() throws Exception {
        this.mockMvc.perform(post(HISTORIC_CREADOR)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("idUsuariCreador", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void creador_ok() throws Exception {
        this.mockMvc.perform(post(CREADOR)
                        .param("idUsuariCreador", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)));
    }
}
