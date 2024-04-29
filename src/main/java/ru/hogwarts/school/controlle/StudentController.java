package ru.hogwarts.school.controlle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequestMapping("student")
@RestController
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService,StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        Student user = studentService.getStudentById(studentId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudentsByAge(@RequestParam(required = false) Integer age) {
        if (age != null && age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/between")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam(required = false) Integer ageMin,
                                                                @RequestParam(required = false) Integer ageMax) {
        if (ageMin > 0 && ageMax > ageMin) {
            return ResponseEntity.ok(studentService.findByAgeBetween(ageMin, ageMax));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.findById(id).map(Student::getFaculty).orElse(null);
    }

    @GetMapping("/students/count")
    public ResponseEntity<Integer> getCountAllStudents() {
        Integer count = studentRepository.countAllStudents();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/students/average-age")
    public ResponseEntity<Integer> getAverageAge() {
        Integer averageAge = studentRepository.getAverageAge();
        return ResponseEntity.ok(averageAge);
    }
    @GetMapping("/students/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        Page<Student> page = studentRepository.findLastFiveStudents(PageRequest.of(0, 5));
        return ResponseEntity.ok(page.getContent());
    }

}
