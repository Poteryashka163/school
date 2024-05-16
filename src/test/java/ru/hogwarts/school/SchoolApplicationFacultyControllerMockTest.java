package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controlle.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
public class SchoolApplicationFacultyControllerMockTest {
    Faculty faculty = new Faculty();


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;


    @Test
    public void createFacultyTest() throws Exception {
        faculty.setId(4L);
        faculty.setName("MockTestNameFaculty");
        faculty.setColor("White");
        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);
        String jsonContent = "{\"name\":\"MockTestNameFaculty\",\"color\":\"White\"}";
        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.name").value("MockTestNameFaculty"))
                .andExpect(jsonPath("$.color").value("White"));
    }

    @Test
    public void getFacultyTest() throws Exception {
        Long facultyId = 4L;
        faculty.setId(facultyId);
        faculty.setName("MockTestNameFaculty");
        faculty.setColor("White");
        when(facultyService.getFacultyById(facultyId)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/{facultyId}", facultyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MockTestNameFaculty"))
                .andExpect(jsonPath("$.color").value("White"));
    }


    @Test
    public void updateFacultyTest() throws Exception {
        faculty.setId(4L);
        faculty.setName("MockTestNameFaculty");
        faculty.setColor("White");
        when(facultyService.updateFaculty(any(Faculty.class))).thenReturn(faculty);

        String jsonContent = "{\"id\":1,\"name\":\"MockTestNameFaculty\",\"color\":\"White\"}";

        mockMvc.perform(put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.name").value("MockTestNameFaculty"))
                .andExpect(jsonPath("$.color").value("White"));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Long facultyId = 4L;
        doNothing().when(facultyService).deleteFaculty(facultyId);

        mockMvc.perform(delete("/faculty/{facultyId}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findFacultiesByColorTest() throws Exception {
        faculty.setId(4L);
        faculty.setName("MockTestNameFaculty");
        faculty.setColor("White");
        List<Faculty> faculties = List.of(faculty);
        when(facultyService.findByColor("White")).thenReturn(faculties);

        mockMvc.perform(get("/faculty?color=White")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4L))
                .andExpect(jsonPath("$[0].name").value("MockTestNameFaculty"))
                .andExpect(jsonPath("$[0].color").value("White"));
    }

    @Test
    public void findFacultiesByNameOrColorTest() throws Exception {
        faculty.setId(4L);
        faculty.setName("MockTestNameFaculty");
        faculty.setColor("White");
        List<Faculty> faculties = List.of(faculty);
        when(facultyService.findByNameIgnoreCaseOrColorIgnoreCase("MockTestNameFaculty", "white")).thenReturn(faculties);

        mockMvc.perform(get("/faculties/search")
                        .param("query", "white")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4L))
                .andExpect(jsonPath("$[0].name").value("MockTestNameFaculty"))
                .andExpect(jsonPath("$[0].color").value("White"));
    }

    @Test
    public void getFacultyStudentsTest() throws Exception {
        Student student = new Student();
        student.setAge(33);
        student.setName("Alice");
        Student student2 = new Student();
        student.setAge(41);
        student.setName("Bob");
        List<Student> students = List.of(student, student2);
        Long facultyId = 1L;
        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setStudents(students);

        when(facultyService.findById(facultyId)).thenReturn(java.util.Optional.of(faculty));

        mockMvc.perform(get("/faculty/{id}/student", facultyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

}

