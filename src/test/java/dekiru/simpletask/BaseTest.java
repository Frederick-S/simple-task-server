package dekiru.simpletask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dekiru.simpletask.dto.UserLoginRequest;
import dekiru.simpletask.entity.User;
import dekiru.simpletask.repository.UserRepository;
import dekiru.simpletask.service.PasswordService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class BaseTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    protected User currentUser;

    protected User otherUser;

    private String password = "123";

    protected static ObjectMapper objectMapper;

    @BeforeAll
    public static void beforeAll() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void beforeEach() {
        currentUser = new User();
        currentUser.setName(UUID.randomUUID().toString());
        currentUser.setEncryptedPassword(passwordService.encrypt(password));
        currentUser.setCreatedAt(Instant.now());

        otherUser = new User();
        otherUser.setName(UUID.randomUUID().toString());
        otherUser.setEncryptedPassword(passwordService.encrypt(password));
        otherUser.setCreatedAt(Instant.now());

        userRepository.save(currentUser);
        userRepository.save(otherUser);
    }

    protected Cookie[] login() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setName(currentUser.getName());
        userLoginRequest.setPassword(password);

        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(userLoginRequest);
        Cookie[] cookies = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(data))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getCookies();

        return cookies;
    }

    protected User createUser(String password) {
        User user = new User();
        user.setName(UUID.randomUUID().toString().substring(0, 20));
        user.setEncryptedPassword(passwordService.encrypt(password));
        user.setCreatedAt(Instant.now());

        userRepository.save(user);

        return user;
    }
}
