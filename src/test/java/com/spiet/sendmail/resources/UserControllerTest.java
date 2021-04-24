package com.spiet.sendmail.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spiet.sendmail.DTOs.UserDTO;
import com.spiet.sendmail.domain.User;
import com.spiet.sendmail.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final static String BASE_URL = "/users";

    @Autowired
    MockMvc mvc;

    @MockBean
    IUserService service;

    @Test
    @DisplayName("Deve Criar um usuário")
    void createUserTest() throws Exception{
        UserDTO userDTO = UserDTO.builder().name("Jon Doe").email("JonDoe@email.com").build();
        User user = new ModelMapper().map(userDTO, User.class);

        String json = new ObjectMapper().writeValueAsString(userDTO);

        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        BDDMockito.given( service.createUser(Mockito.any(UserDTO.class)))
                .willReturn(user);

        mvc.perform(req)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Jon Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("JonDoe@email.com"));

    }

    @Test
    @DisplayName("deve retornar um usuário por id")
    void returnUserById() throws Exception{
        Long id = 1L;

        UserDTO userDTO = UserDTO.builder().name("Jon Doe").email("JonDoe@email.com").build();
        User user = new ModelMapper().map(userDTO, User.class);
        user.setId(id);

        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get(BASE_URL.concat("/"+id));

        BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(user));

        Optional<User> result = service.findById(id);

        mvc.perform(req)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(result.get().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(result.get().getEmail()));
    }

    @Test
    @DisplayName("Deve retornar 404 quando o usuario não for encontrado")
    void return404ifUserDoenstExist() throws Exception{
        Long id = 1L;

        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get(BASE_URL.concat("/"+id));

        BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());

        mvc.perform(req)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
