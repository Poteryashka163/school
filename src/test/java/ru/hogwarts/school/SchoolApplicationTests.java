package ru.hogwarts.school;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controlle.FacultyController;
import ru.hogwarts.school.controlle.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationStudentControllerTests {
	@LocalServerPort
	private int port;

	@Autowired
	private StudentController studentController;
	@Autowired
	private FacultyController facultyController;

	@Autowired
	private TestRestTemplate restTemplate;

	private String baseUrl() {
		return "http://localhost:" + port + "/student/";
	}
	@Test
	public void TestCreateStudents()  {
		Faculty testFaculty = new Faculty();
		testFaculty.setName("Test Faculty");
		testFaculty.setColor("Red");
		Faculty createdFaculty=facultyController.createFaculty(testFaculty).getBody();

		Student testStudent = new Student();
		testStudent.setName("TestName");
		testStudent.setAge(99);
		testStudent.setFaculty(createdFaculty);



		Assertions.
				assertThat(this.restTemplate.postForObject(baseUrl(), testStudent, Student.class));
				assertThat(testStudent.getAge()).isEqualTo(99);
				assertThat(testStudent.getName()).isEqualTo("TestName");
				assertThat(testFaculty.getName()).isEqualTo("Test Faculty");
				assertThat(testFaculty.getColor()).isEqualTo("Red");



	}


	@Test
	void contextLoads() {
	}

}
