package com.example.restful;

import com.example.restful.model.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAppIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static TestRestTemplate testRestTemplate;

    @Autowired
    private TestH2Repository testH2Repository;

    @BeforeAll
    public static void init() {
        testRestTemplate = new TestRestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/users");
    }

    @AfterEach
    public void tearDown() {
        testH2Repository.deleteAll();
    }

    @Test
    public void addUser() {
        User expectedUser = new User(1L, "Javid", "Alakbarli", 19);
        User actualUser = testRestTemplate.postForObject(baseUrl, expectedUser, User.class);
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    @Sql(statements = "INSERT INTO USERS(first_name, last_name, age) VALUES ('Ilqar', 'Aliyev', 22)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getTestUsers() {
        List<User> actualUsers = testRestTemplate.getForObject(baseUrl, List.class);
        System.out.println(actualUsers);
        assertNotNull(actualUsers);
        assertEquals(4, actualUsers.size());
        assertEquals(4, testH2Repository.findAll().size());
    }

    @Test
    public void getTestUserById() {
        User expectedUser = new User(1L, "Javid", "Alakbarli", 19);
        User actualUser = testRestTemplate.getForObject(baseUrl + "/" + expectedUser.getId(), User.class);
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void updateUser() {
        User expectedUser = new User(1L, "Ilqar", "Aliyev", 22);
        testRestTemplate.postForObject(baseUrl + "/" + expectedUser.getId(), expectedUser, User.class);
        User actualUser = testRestTemplate.getForObject(baseUrl + "/" + expectedUser.getId(), User.class);
        assertNotNull(actualUser);
        assertNotEquals(expectedUser, actualUser);
    }

    @Test
    public void partialUpdateUser() {
        User expectedUser = new User(1L, "Javid", "Alakbarli", 19);

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

        testRestTemplate.postForObject(baseUrl + "/" + expectedUser.getId(), expectedUser, User.class);
        User actualUser = testRestTemplate.getForObject(baseUrl + "/" + expectedUser.getId(), User.class);
        assertNotNull(actualUser);
        assertNotEquals(expectedUser, actualUser);
    }

    @Test
    public void deleteTestUserById() {
        User expectedUser = new User(1L, "Javid", "Alakbarli", 19);
        testRestTemplate.delete(baseUrl + "/" + expectedUser.getId());
        Optional<User> actualUser = testH2Repository.findById(expectedUser.getId());
        assertNotEquals(expectedUser, actualUser.orElse(null));
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }
}
