package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controlle.FacultyController;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationFacultyControllerTest {
    Faculty testFaculty = new Faculty();
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    @Test
    public void TestCreateFaculty() {
        testFaculty.setName("Тестовый Факультет");
        testFaculty.setColor("Синий");
        ResponseEntity<Faculty> createFaculty = restTemplate.postForEntity(baseUrl(), testFaculty, Faculty.class);
        Assertions.
                assertThat(createFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createFaculty.getBody().getName()).isEqualTo("Тестовый Факультет");
        assertThat(createFaculty.getBody().getColor()).isEqualTo("Синий");
    }

    @Test
    public void TestGetFaculty() {
        long testId = 15L;// Подставляем необходимый ID.
        ResponseEntity<Faculty> getFaculty = restTemplate.getForEntity(baseUrl() + "/{facultyId}", Faculty.class, testId);
        Assertions.
                assertThat(getFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getFaculty.getBody().getId()).isEqualTo(testId);
        assertThat(getFaculty.getBody().getName()).isEqualTo("Тестовый Факультет");
        assertThat(getFaculty.getBody().getColor()).isEqualTo("Синий");

    }

    @Test
    public void TestUpdateFaculty() {
        testFaculty.setName("Test Faculty");
        testFaculty.setColor("Red");
        Faculty createdFaculty = facultyController.createFaculty(testFaculty).getBody();


        HttpEntity<Faculty> requestUpdate = new HttpEntity<>(testFaculty);
        ResponseEntity<Faculty> createStudent = restTemplate.exchange(baseUrl(), HttpMethod.PUT, requestUpdate, Faculty.class);
        Assertions.
                assertThat(createStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdFaculty.getName()).isEqualTo("Test Faculty");
        assertThat(createdFaculty.getColor()).isEqualTo("Red");

    }

    @Test
    public void TestDeleteFaculty() {
        long testId = 16L;// Подставляем необходимый ID.
        ResponseEntity<Void> getFaculty = restTemplate.exchange(baseUrl() + testId, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        Assertions.
                assertThat(getFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void TestFindFacultiesByColor() {
        String testColor = "Red";// Подставляем необходимый возраст.
        ResponseEntity<Collection> response = restTemplate.getForEntity(baseUrl() + "?color=" + testColor, Collection.class);
        Assertions.
                assertThat(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);

    }

    @Test
    public void TestFindFacultiesByNameOrColor() {
        String query = "Engineering"; // Вводим цвет или название факультета.
        ResponseEntity<Collection> response = restTemplate.getForEntity(baseUrl() + "/search?query={query}", Collection.class, query);
        Assertions.
                assertThat(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void TestGetFacultyStudents() {
        long facultyId = 1L; // Подставляем необходимый ID.
        ResponseEntity<Collection> response = restTemplate.getForEntity("/{id}/students", Collection.class, facultyId);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);
    }

}
