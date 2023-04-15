package dekiru.simpletask.controller;

import dekiru.simpletask.BaseTest;
import dekiru.simpletask.dto.UserLoginRequest;
import dekiru.simpletask.entity.User;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

public class AuthControllerTest extends BaseTest {
    @Test
    public void shouldReturn400WhenLoginAndPostBodyIsInvalid() throws Exception {
        User user = createUser("password");
        UserLoginRequest userLoginRequest1 = new UserLoginRequest();
        UserLoginRequest userLoginRequest2 = new UserLoginRequest();
        userLoginRequest2.setName("example@example.com");
        UserLoginRequest userLoginRequest3 = new UserLoginRequest();
        userLoginRequest3.setName(currentUser.getName());
        userLoginRequest3.setPassword("abc");
        UserLoginRequest userLoginRequest4 = new UserLoginRequest();
        userLoginRequest4.setName(user.getName());
        userLoginRequest4.setPassword("password1");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userLoginRequest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userLoginRequest2)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userLoginRequest3)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userLoginRequest4)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturn404WhenLoginAndUserNotFound() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setName(UUID.randomUUID().toString().substring(0, 20));
        userLoginRequest.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldLoginUser() throws Exception {
        User user = createUser("password");
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setName(user.getName());
        userLoginRequest.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldSignOutUser() throws Exception {
        Cookie[] cookies = login();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signOut").cookie(cookies))
                .andExpect(MockMvcResultMatchers.status().isOk());

        String body = mockMvc.perform(MockMvcRequestBuilders.get("/users/me").cookie(cookies))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertTrue(StringUtils.isBlank(body));
    }
}
