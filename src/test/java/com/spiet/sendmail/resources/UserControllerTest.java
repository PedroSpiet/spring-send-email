package com.spiet.sendmail.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spiet.sendmail.DTOs.UserDTO;
import com.spiet.sendmail.domain.User;
import com.spiet.sendmail.service.IUserService;
import com.spiet.sendmail.service.impl.EmailService;
import org.hamcrest.Matchers;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
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

    @MockBean
    EmailService emailService;

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

    @Test
    @DisplayName("Deve filtrar um livro")
    public void fitlerBook() throws Exception {
        Long id = 1L;
        UserDTO userDTO = UserDTO.builder().name("Jon Doe").email("JonDoe@email.com").build();
        User user = new ModelMapper().map(userDTO, User.class);
        user.setId(id);

        BDDMockito.given(service.find(Mockito.any(UserDTO.class), Mockito.any(Pageable.class)))
                .willReturn(new PageImpl<User>(Arrays.asList(user), PageRequest.of(0, 10), 1));

        // /users

        String queryString = String.format("?name=%s&email=%s&page=0&size=10", user.getName(), user.getEmail());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BASE_URL.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("pageable.pageSize").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("pageable.pageNumber").value(0));
    }
}
