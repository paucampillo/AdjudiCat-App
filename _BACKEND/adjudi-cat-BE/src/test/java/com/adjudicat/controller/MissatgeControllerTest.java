package com.adjudicat.controller;

import com.adjudicat.configuration.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

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
public class MissatgeControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/v1/adj/missatge";
    private static final String ENVIAR = BASE + "/enviar";
    private static final String HISTORIC = BASE + "/historic";
    private static final String CONVERSACIONS = BASE + "/conversacions";
    private static final String DELETE = BASE + "/deleteConverse";

    @Test
    public void enviar_ok() throws Exception {
        this.mockMvc.perform(post(ENVIAR)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("idEmisor", "2")
                        .param("idReceptor", "3")
                        .param("mensaje", "Hola")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMissatge_ok() throws Exception {
        this.mockMvc.perform(delete(BASE + "/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void findPaginated_ok() throws Exception {
        this.mockMvc.perform(post(HISTORIC)
                .param("idEmisor", "2")
                .param("idReceptor", "1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void findConverses_ok() throws Exception {
        this.mockMvc.perform(get(CONVERSACIONS)
                .param("idUsuari", "1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void deleteConverse_ok() throws Exception {
        this.mockMvc.perform(delete(DELETE)
                .param("idEmissor", "1")
                .param("idReceptor", "2")
        )
                .andExpect(status().isOk());
    }
}
