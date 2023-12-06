package edu.uoc.workoutwizardusermanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uoc.workoutwizardusermanagement.WorkoutWizardUserManagementApplication;
import edu.uoc.workoutwizardusermanagement.controller.dtos.CreateUserRequest;
import edu.uoc.workoutwizardusermanagement.model.User;
import edu.uoc.workoutwizardusermanagement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WorkoutWizardUserManagementApplication.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        user = userRepository.save(User.builder()
                .username("testuser")
                .email("test@mail.com")
                .password("password123")
                .build());
    }

    @Test
    public void testCreateUser() throws Exception {
        final var request = new CreateUserRequest("testuser", "test@example.com", "password123");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.password").value(not("password123")));
    }

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@mail.com"))
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.password").value(not("password123")));
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isOk());
    }
}
