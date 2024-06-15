package com.adjudicat.controller;

import com.adjudicat.configuration.BaseTest;
import com.adjudicat.controller.dto.ChatbotAddDTO;
import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FiltreDTO;
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
public class ChatbotControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/v1/adj/chatbot";
    private static final String CONSULTA = BASE + "/consulta";
    private static final String CONSULTA_EXTERNA = BASE + "/consultaExterna";
    private static final String NUEVA_PALABRA = BASE + "/nuevaPalabra";

    @Test
    public void consulta_ok() throws Exception {
        mockMvc.perform(post(CONSULTA)
                .param("idEmissor", "1")
                .param("mensaje", "¿Cómo puedo licitar en una oferta activa?")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void consulta_ok2() throws Exception {
        mockMvc.perform(post(CONSULTA)
                        .param("idEmissor", "1")
                        .param("mensaje", "¿Cómo puedo licitar en una oferta activa? ¿Qué documentación necesito? ¿Cuál es el plazo de presentación? ¿Dónde puedo presentar la oferta? ¿Qué pasa si no cumplo con los requisitos? ¿Qué pasa si no presento la oferta a tiempo?")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.missatge").value("Tu consulta es demasiado larga; prueba a usar menos de 25 palabras."));
    }

    @Test
    public void consulta_ok3() throws Exception {
        mockMvc.perform(post(CONSULTA)
                        .param("idEmissor", "1")
                        .param("mensaje", "No hay palabras clave")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.missatge").value("No he encontrado ningún artículo relativo a la consulta. Por favor, inténtalo con otras palabras."));
    }

    @Test
    public void consultaExterna_ok() throws Exception {
        mockMvc.perform(post(CONSULTA_EXTERNA)
                .param("mensaje", "licitar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void nuevaPalabra_ok() throws Exception {
        ChatbotAddDTO chatbotAddDTO = DTOHelper.getChatbotAddDTO();
        mockMvc.perform(post(NUEVA_PALABRA)
                .content(new ObjectMapper().writeValueAsString(chatbotAddDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
