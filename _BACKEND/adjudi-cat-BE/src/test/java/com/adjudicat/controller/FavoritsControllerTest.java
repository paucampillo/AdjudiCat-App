package com.adjudicat.controller;

import com.adjudicat.configuration.BaseTest;
import com.adjudicat.controller.dto.ReturnFavoritsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

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
public class FavoritsControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/v1/adj/favorits";
    private static final String ADD = BASE + "/add";
    private static final String DELETE = BASE + "/delete";
    private static final String LIST = BASE + "/list";

    @Test
    public void add_ok() throws Exception {
        ReturnFavoritsDTO returnFavoritsDTO = ReturnFavoritsDTO.builder()
                .idUsuari(2L)
                .idContracte(6L)
                .build();
        mockMvc.perform(post(ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(returnFavoritsDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void add_ko() throws Exception {
        ReturnFavoritsDTO returnFavoritsDTO = ReturnFavoritsDTO.builder()
                .idUsuari(9999L)
                .idContracte(9999L)
                .build();
        mockMvc.perform(post(ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(returnFavoritsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete_ok() throws Exception {
        ReturnFavoritsDTO returnFavoritsDTO = ReturnFavoritsDTO.builder()
                .idUsuari(2L)
                .idContracte(1L)
                .build();
        mockMvc.perform(post(DELETE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(returnFavoritsDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_ko() throws Exception {
        ReturnFavoritsDTO returnFavoritsDTO = ReturnFavoritsDTO.builder()
                .idUsuari(9999L)
                .idContracte(9999L)
                .build();
        mockMvc.perform(post(DELETE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(returnFavoritsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void list_ok() throws Exception {
        this.mockMvc.perform(post(LIST)
                        .param("id_user", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }
}
