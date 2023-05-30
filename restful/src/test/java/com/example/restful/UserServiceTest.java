package com.example.restful;

import com.example.restful.model.dto.UserDto;
import com.example.restful.model.entity.User;
import com.example.restful.model.mapper.UserMapper;
import com.example.restful.repository.UserRepository;
import com.example.restful.service.UserService;
import com.example.restful.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User expectedUser;

    @BeforeEach
    public void setUp() {
        expectedUser = new User(1L, "Javid", "Alakbarli", 19);
    }

    @Test
    @DisplayName("saveUser_WhenCalled_ShouldReturnExpectedEntity")
    void saveUser_WhenCalled_ShouldReturnExpectedEntity() {
        //Given

        //Mock User Storage Repository
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserServiceImpl(mockedUserRepository);
        when(mockedUserRepository.save(expectedUser)).thenReturn(expectedUser);

        //When
        User actualUser = userService.saveUser(expectedUser);

        //Then
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
        verify(mockedUserRepository, times(1)).save(expectedUser);
    }

    @Test
    @DisplayName("listUsers_WhenCalled_ShouldReturnExpectedList")
    void listUsers_WhenCalled_ShouldReturnExpectedList() {
        //Given
        List<User> expectedUsers = List.of(
                new User(1L, "Javid", "Alakbarli", 19),
                new User(2L, "Mushfig", "Jafarov", 18)
        );

        //Mock User Storage Repository
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserServiceImpl(mockedUserRepository);
        when(mockedUserRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"))).thenReturn(expectedUsers);

        //When
        List<User> actualUsers = userService.listUsers();

        //Then
        assertNotNull(actualUsers);
        assertEquals(expectedUsers, actualUsers);
        verify(mockedUserRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));
    }

    @Test
    @DisplayName("getUserById_WhenCalled_ShouldReturnExpectedList")
    void getUserById_WhenCalled_ShouldReturnExpectedList() {
        //Given

        //Mock User Storage Repository
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserServiceImpl(mockedUserRepository);
        when(mockedUserRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        //When
        Optional<User> actualUser = userService.getUserById(expectedUser.getId());

        //Then
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser.get());
        Mockito.verify(mockedUserRepository).findById(expectedUser.getId());
    }

    @Test
    @DisplayName("updateUser_WhenCalled_ShouldReturnExpectedList")
    void updateUser_WhenCalled_ShouldReturnExpectedList() {
        //Given
        User updateExpectedUser = new User(expectedUser.getId(), "Ilkin", "Rzayev", 67);

        //Mock User Storage Repository
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserServiceImpl(mockedUserRepository);
        when(mockedUserRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        when(mockedUserRepository.save(updateExpectedUser)).thenReturn(updateExpectedUser);

        //When
        Optional<User> actualUser = userService.updateUser(expectedUser.getId(), updateExpectedUser);

        //Then
        assertNotNull(actualUser);
        assertEquals(updateExpectedUser, actualUser.get());
        Mockito.verify(mockedUserRepository).findById(actualUser.get().getId());
        Mockito.verify(mockedUserRepository).save(actualUser.get());
    }

    @Test
    @DisplayName("partialUpdateUser_WhenCalled_ShouldReturnExpectedList")
    void partialUpdateUser_WhenCalled_ShouldReturnExpectedList() {
        //Given
        Map<String, Object> params = new HashMap<>();
        params.put("age", 54);

        params.forEach((k, v) -> {
            try {
                Field field = ReflectionUtils.findField(User.class, k);
                field.setAccessible(true);
                if (v instanceof String && isNumeric((String) v)) {
                    Integer numericValue = Integer.parseInt((String) v);
                    field.set(expectedUser, numericValue);
                } else {
                    field.set(expectedUser, v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Mock User Storage Repository
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserServiceImpl(mockedUserRepository);
        when(mockedUserRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        when(mockedUserRepository.save(expectedUser)).thenReturn(expectedUser);

        //When
        Optional<User> actualUser = userService.partialUpdateUser(expectedUser.getId(), params);

        //Then
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser.get());
        Mockito.verify(mockedUserRepository).findById(actualUser.get().getId());
        Mockito.verify(mockedUserRepository).save(actualUser.get());
    }

    @Test
    @DisplayName("deleteUserById_WhenCalled_ShouldNotReturn")
    void deleteUserById_WhenCalled_ShouldNotReturn() {
        //Given

        // Mock User Storage Repository
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserServiceImpl(mockedUserRepository);

        // When
        userService.deleteUserById(expectedUser.getId());

        // Then
        Mockito.verify(mockedUserRepository).deleteById(expectedUser.getId());
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }
}