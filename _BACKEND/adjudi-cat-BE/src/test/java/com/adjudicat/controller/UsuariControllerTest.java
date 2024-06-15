package com.adjudicat.controller;

import com.adjudicat.configuration.BaseTest;
import com.adjudicat.controller.dto.CanviContrasenyaDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.helper.DTOHelper;
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
public class UsuariControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/v1/adj/usuari";
    private static final String EDIT = BASE + "/edit";
    private static final String PASSWORD = BASE + "/password";
    private static final String LOGIN = BASE + "/login";
    private static final String LOGIN_GOOGLE = BASE + "/loginGoogle";
    private static final String BLOQUEAR = BASE + "/bloquear";
    private static final String DESBLOQUEAR = BASE + "/desbloquear";
    private static final String ALL_USERS = BASE + "/allUsers";

    @Test
    public void login_ok() throws Exception {
        this.mockMvc.perform(post(LOGIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("email", "admin@localhost")
                .param("password", "admin")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginCorrect").value(true));
    }

    @Test
    public void login_ok2() throws Exception {
        this.mockMvc.perform(post(LOGIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("email", "admin@localhost")
                .param("password", "badPassword")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginCorrect").value(false));
    }

    @Test
    public void login_ok3() throws Exception {
        this.mockMvc.perform(post(LOGIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("email", "badEmail@localhost")
                .param("password", "admin")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginCorrect").value(false));
    }

    @Test
    public void login_ok4() throws Exception {
        this.mockMvc.perform(post(LOGIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("email", "bloquejat@localhost")
                        .param("password", "bloquejat")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginCorrect").value(false));
    }

    @Test
    public void loginGoogle_ok() throws Exception {
        this.mockMvc.perform(post(LOGIN_GOOGLE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("email", "admin@localhost")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginCorrect").value(true));
    }

    @Test
    public void loginGoogle_ok2() throws Exception {
        this.mockMvc.perform(post(LOGIN_GOOGLE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("email", "bloquejat@localhost")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginCorrect").value(false));
    }

    @Test
    public void get_ok() throws Exception {
        this.mockMvc.perform(get(BASE + "/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuari").value(3));
    }

    @Test
    public void get_ko() throws Exception {
        this.mockMvc.perform(get(BASE + "/9999999")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void editar_ok() throws Exception {
        UsuariDTO usuariDTO = DTOHelper.getUsuariDTO();
        usuariDTO.setCodiPostal(8902);
        this.mockMvc.perform(put(EDIT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(usuariDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codiPostal").value(8902));
    }

    @Test
    public void editar_ko() throws Exception {
        UsuariDTO usuariDTO = DTOHelper.getUsuariDTO();
        usuariDTO.setIdUsuari(9999999L);
        this.mockMvc.perform(put(EDIT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(usuariDTO))
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void canviContrasenya_ok() throws Exception {
        CanviContrasenyaDTO canviContrasenyaDTO = CanviContrasenyaDTO.builder()
                .idUsuari(1L)
                .lastPassword("admin")
                .newPassword("newPassowrd").build();
        this.mockMvc.perform(put(PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(canviContrasenyaDTO))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void canviContrasenya_ko_1() throws Exception {
        CanviContrasenyaDTO canviContrasenyaDTO = CanviContrasenyaDTO.builder()
                .idUsuari(99999999L)
                .lastPassword("admin")
                .newPassword("newPassowrd").build();
        this.mockMvc.perform(put(PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(canviContrasenyaDTO))
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void canviContrasenya_ko_2() throws Exception {
        CanviContrasenyaDTO canviContrasenyaDTO = CanviContrasenyaDTO.builder()
                .idUsuari(1L)
                .lastPassword("badPassword")
                .newPassword("newPassowrd").build();
        this.mockMvc.perform(put(PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(canviContrasenyaDTO))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void bloquear_ok() throws Exception {
        this.mockMvc.perform(put(BLOQUEAR)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("idUser", "1")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void desbloquear_ok() throws Exception {
        this.mockMvc.perform(put(DESBLOQUEAR)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("idUser", "4")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void allUsers_ok() throws Exception {
        this.mockMvc.perform(get(ALL_USERS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)));
    }
}
