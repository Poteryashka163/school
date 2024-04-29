package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controlle.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
public class SchoolApplicationStudentControllerMockTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;


    @Test
    public void createStudentTest() throws Exception {
        Student student = new Student();
        student.setId(12l);
        student.setAge(55);
        student.setName("TestNameStudent");
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"TestNameStudent\",\"age\":55}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12L))
                .andExpect(jsonPath("$.name").value("TestNameStudent"))
                .andExpect(jsonPath("$.age").value(55));
    }

    @Test
    public void getStudentTest() throws Exception{
        Student student = new Student();
        student.setId(12l);
        student.setAge(55);
        student.setName("TestNameStudent");
        when(studentService.getStudentById(12L)).thenReturn(student);

        mockMvc.perform(get("/student/{studentId}", 12L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12L))
                .andExpect(jsonPath("$.name").value("TestNameStudent"))
                .andExpect(jsonPath("$.age").value(55));

        when(studentService.getStudentById(12L)).thenReturn(null);
        mockMvc.perform(get("/student/{studentId}", 12L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStudentTest() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(12l);
        updatedStudent.setAge(55);
        updatedStudent.setName("TestNameStudent");
        when(studentService.updateStudent(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":12,\"name\":\"TestNameStudent\",\"age\":55}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestNameStudent"))
                .andExpect(jsonPath("$.age").value(55));

        when(studentService.updateStudent(any(Student.class))).thenReturn(null);
        mockMvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":12,\"name\":\"TestNameStudent\",\"age\":55}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteStudentTest() throws Exception {
        doNothing().when(studentService).deleteStudent(12L);

        mockMvc.perform(delete("/student/{studentId}", 12L))
                .andExpect(status().isOk());
    }

    @Test
    public void findStudentsByAgeTest() throws Exception {
        Student student = new Student();
        student.setId(12L);
        student.setAge(55);
        student.setName("TestNameStudent");
        List<Student> students = List.of(student);
        when(studentService.findByAge(55)).thenReturn(students);

        mockMvc.perform(get("/student").param("age", "55"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("TestNameStudent"))
                .andExpect(jsonPath("$[0].age").value(55));

        when(studentService.findByAge(55)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/student").param("age", "55"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void findByAgeBetweenTest() throws  Exception{
        Student student = new Student();
        student.setId(12l);
        student.setAge(55);
        student.setName("TestNameStudent");
        List<Student> students = List.of(student);
        when(studentService.findByAgeBetween(18, 22)).thenReturn(students);

        mockMvc.perform(get("/student/between")
                        .param("ageMin", "18")
                        .param("ageMax", "22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(12L))
                .andExpect(jsonPath("$[0].name").value("TestNameStudent"))
                .andExpect(jsonPath("$[0].age").value(55));

        when(studentService.findByAgeBetween(18, 22)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/student/between")
                        .param("ageMin", "18")
                        .param("ageMax", "22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void getStudentFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(12L);
        faculty.setName("TestNameFaculty");
        faculty.setColor("Green");
        Student student = new Student();
        student.setId(10L);
        student.setAge(55);
        student.setName("TestNameStudent");
        when(studentService.findById(10L)).thenReturn(java.util.Optional.of(student));

        mockMvc.perform(get("/student/{id}/faculty", 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12L))
                .andExpect(jsonPath("$.name").value("TestNameFaculty"));

        when(studentService.findById(10L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/student/{id}/faculty", 10L))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

}
