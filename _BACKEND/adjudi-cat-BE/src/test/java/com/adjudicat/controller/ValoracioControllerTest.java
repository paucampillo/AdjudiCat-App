package com.adjudicat.controller;

import com.adjudicat.configuration.BaseTest;
import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FiltreDTO;
import com.adjudicat.controller.dto.ValoracioCustomDTO;
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
public class ValoracioControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/v1/adj/valoracio";
    private static final String VALORAR = BASE + "/valorar";
    private static final String ELIMINAR = BASE + "/eliminar";
    private static final String LISTAR_POR_RECEPTOR = BASE + "/listarPorReceptor";
    private static final String LISTAR_POR_EMISOR = BASE + "/listarPorEmisor";

    @Test
    public void valorar_ok() throws Exception {
        ValoracioCustomDTO valoracioCustomDTO = DTOHelper.getValoracioCustomDTO();
        mockMvc.perform(post(VALORAR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(valoracioCustomDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void eliminar_ok() throws Exception {
        mockMvc.perform(delete(ELIMINAR + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void listarPorReceptor_ok() throws Exception {
        mockMvc.perform(get(LISTAR_POR_RECEPTOR)
                .param("idUsuari", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void listarPorEmisor_ok() throws Exception {
        mockMvc.perform(get(LISTAR_POR_EMISOR)
                .param("idUsuari", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }
}
