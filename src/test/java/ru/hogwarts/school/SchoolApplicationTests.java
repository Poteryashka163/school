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
import ru.hogwarts.school.controlle.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationStudentControllerTests {
    Faculty testFaculty = new Faculty();
    Student testStudent = new Student();
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    public void TestCreateStudents() {
        testFaculty.setName("Test Faculty");
        testFaculty.setColor("Red");
        Faculty createdFaculty = facultyController.createFaculty(testFaculty).getBody();

        testStudent.setName("TestName");
        testStudent.setAge(99);
        testStudent.setFaculty(createdFaculty);
        ResponseEntity<Student> createStudent = restTemplate.postForEntity(baseUrl(), testStudent, Student.class);
        Assertions.
                assertThat(createStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createStudent.getBody().getAge()).isEqualTo(99);
        assertThat(createStudent.getBody().getName()).isEqualTo("TestName");
        assertThat(createdFaculty.getName()).isEqualTo("Test Faculty");
        assertThat(createdFaculty.getColor()).isEqualTo("Red");

    }

    @Test
    public void TestGetStudent() {
        long testId = 15L;// Подставляем необходимый ID.
        ResponseEntity<Student> getStudent = restTemplate.getForEntity(baseUrl() + "/{studentId}", Student.class, testId);
        Assertions.
                assertThat(getStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getStudent.getBody().getId()).isEqualTo(testId);
        assertThat(getStudent.getBody().getName()).isEqualTo("TestName");
        assertThat(getStudent.getBody().getAge()).isEqualTo(99);

    }

    @Test
    public void TestUpdateStudent() {
        testFaculty.setName("Test Faculty");
        testFaculty.setColor("Red");
        Faculty createdFaculty = facultyController.createFaculty(testFaculty).getBody();

        testStudent.setName("UpdateTestName");
        testStudent.setAge(36);
        testStudent.setId(16L);// Подставляем необходимый ID.
        testStudent.setFaculty(createdFaculty);
        HttpEntity<Student> requestUpdate = new HttpEntity<>(testStudent);
        ResponseEntity<Student> createStudent = restTemplate.exchange(baseUrl(), HttpMethod.PUT, requestUpdate, Student.class);
        Assertions.
                assertThat(createStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createStudent.getBody().getAge()).isEqualTo(36);
        assertThat(createStudent.getBody().getName()).isEqualTo("UpdateTestName");
        assertThat(createdFaculty.getName()).isEqualTo("Test Faculty");
        assertThat(createdFaculty.getColor()).isEqualTo("Red");

    }

    @Test
    public void TestDeleteStudent() {
        long testId = 16L;// Подставляем необходимый ID.
        ResponseEntity<Void> getStudent = restTemplate.exchange(baseUrl() + testId, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        Assertions.
                assertThat(getStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void TestfindStudentsByAge() {
        long testAge = 16;// Подставляем необходимый возраст.
        ResponseEntity<Collection> response = restTemplate.getForEntity("?age=" + testAge, Collection.class);
        Assertions.
                assertThat(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);

    }

    @Test
    public void TestfindByAgeBetween() {
        int testMinAge = 10;// Вводим необходимые значения.
        int testMaxAge = 20;
        ResponseEntity<Collection> response = restTemplate.getForEntity(baseUrl() + "/between?ageMin=" + testMinAge + "&" + "ageMax" + testMaxAge, Collection.class);
        Assertions.
                assertThat(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);

    }

    @Test
    public void TestgetStudentFaculty() {
        Long studentId = 16L; // Вводим существующий ID.
        ResponseEntity<Faculty> response = restTemplate.getForEntity(baseUrl() + "/{id}/faculty", Faculty.class, studentId);
        Assertions.
                assertThat(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Expected Faculty Name");// Вводим существующий факультет.

    }

    @Test
    void contextLoads() {
    }

}
