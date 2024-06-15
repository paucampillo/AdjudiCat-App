package com.adjudicat.controller;

import com.adjudicat.configuration.BaseTest;
import com.adjudicat.controller.dto.LicitarDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@AutoConfigureMockMvc
@Sql({
        "/db/sql/instanciar_tablas.sql",
        "/db/sql/llenar_datos.sql"
})
public class OfertaControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/v1/adj/oferta";
    private static final String LICITAR = BASE + "/licitar";
    private static final String HISTORIC_OFERTES = BASE + "/historicOfertes";
    private static final String HISTORIC_OFERTES_BY_USUARI = BASE + "/historicOfertesByUsuari";
    private static final String OFERTES_BY_USUARI = BASE + "/ofertesByUsuari";

    @Test
    public void licitar_ok() throws Exception {
        LicitarDTO licitarDTO = LicitarDTO.builder()
                .idUsuari(2L)
                .idContracte(7L)
                .quantitat(100000.0).build();
        this.mockMvc.perform(post(LICITAR)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(licitarDTO))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getHistoricOfertes_ok() throws Exception {
        this.mockMvc.perform(get(HISTORIC_OFERTES + "/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getHistoricOfertesByUsuari_ok() throws Exception {
        this.mockMvc.perform(post(HISTORIC_OFERTES_BY_USUARI)
                .param("idUsuari", "2")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void getOfertesByUsuari_ok() throws Exception {
        this.mockMvc.perform(post(OFERTES_BY_USUARI)
                .param("idUsuari", "2")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }
}
