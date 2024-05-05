package spring.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import spring.config.ApplicationConfig;
import spring.entity.Department;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static spring.repository.AppPropertiesForTest.DATABASE_NAME;
import static spring.repository.AppPropertiesForTest.DATASOURCE_PASSWORD;
import static spring.repository.AppPropertiesForTest.DATASOURCE_USERNAME;
import static spring.repository.AppPropertiesForTest.DOCKER_IMAGE_NAME;
import static spring.repository.AppPropertiesForTest.INIT_SCRIPT_NAME;
import static spring.repository.AppPropertiesForTest.PASSWORD;
import static spring.repository.AppPropertiesForTest.URL;
import static spring.repository.AppPropertiesForTest.USERNAME;


@RunWith(SpringRunner.class)
@SpringJUnitConfig
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfig.class})
@Testcontainers
class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository departmentRepository;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME.getName())
            .withDatabaseName(DATABASE_NAME.getName())
            .withUsername(USERNAME.getName())
            .withPassword(PASSWORD.getName())
            .withInitScript(INIT_SCRIPT_NAME.getName());

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add(URL.getName(), postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add(DATASOURCE_USERNAME.getName(), postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add(DATASOURCE_PASSWORD.getName(), postgreSQLContainer::getPassword);
    }

    @Test
    @DisplayName("Test departmentRepository.findByIdWithProfessor() exists Department with such Id")
    void findByIdWithProfessor_should_getDepartment() {
        Optional<Department> actual = departmentRepository.findByIdWithProfessor(1);
        assertAll(
                () -> assertTrue(Objects.nonNull(actual)),
                () -> assertEquals(1, actual.get().getId()),
                () -> assertEquals("Biology department", actual.get().getName()),
                () -> assertEquals(4, actual.get().getProfessors().size())
        );
    }

    @Test
    @DisplayName("Test departmentRepository.findAll()")
    void findAll_should_getDepartments() {
        List<Department> actual = departmentRepository.findAll();
        assertEquals(5, actual.size());
    }

    @Test
    @DisplayName("Test departmentRepository.existsByName() exists Department with such name")
    void existsByName_should_getTrue() {
        boolean actual = departmentRepository.existsByName("Biology department");
        assertTrue(actual);
    }

    @Test
    @DisplayName("Test departmentRepository.existsByName() doesn't exist Department with such name")
    void existsByName_should_getFalse() {
        boolean actual = departmentRepository.existsByName("Not such name");
        assertFalse(actual);
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }
}