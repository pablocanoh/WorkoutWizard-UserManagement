//package edu.uoc.workoutwizardusermanagement.services;
//
//import edu.uoc.workoutwizardusermanagement.domain.User;
//import edu.uoc.workoutwizardusermanagement.repositories.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testFindAll() {
//        User user = new User(/* initialize your user */);
//        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
//
//        List<User> users = userService.findAll();
//
//        assertEquals(1, users.size());
//        verify(userRepository).findAll();
//    }
//
//    @Test
//    public void testCreateUser() {
//        String username = "testUser";
//        String email = "test@example.com";
//        String password = "password123";
//        User user = User.builder().username(username).email(email).password(password).build();
//
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        User createdUser = userService.createUser(username, password);
//
//        assertEquals(username, createdUser.getUsername());
//        assertEquals(email, createdUser.getEmail());
//        // Don't assert password directly for security reasons
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    public void testDeleteUser() {
//        UUID userId = UUID.randomUUID();
//        User user = new User(/* initialize your user */);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        doNothing().when(userRepository).delete(user);
//
//        User deletedUser = userService.deleteUser(userId);
//
//        assertEquals(user, deletedUser);
//        verify(userRepository).findById(userId);
//        verify(userRepository).delete(user);
//    }
//
//    @Test
//    public void testGetUser() {
//        UUID userId = UUID.randomUUID();
//        User user = new User(/* initialize your user */);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        User foundUser = userService.getUser(userId);
//
//        assertEquals(user, foundUser);
//        verify(userRepository).findById(userId);
//    }
//}
