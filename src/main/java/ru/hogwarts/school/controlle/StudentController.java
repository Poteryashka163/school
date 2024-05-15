package ru.hogwarts.school.controlle;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@RequestMapping("student")
@RestController
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping("/studentId/{studentId}")
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

    @GetMapping("/count")
    public ResponseEntity<Long> getCountAllStudents() {
        Long count = studentService.count();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/find-average-age")
    public ResponseEntity<Integer> getAverageAge() {
        Integer averageAge = studentService.findAverageAge();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        List<Student> page = studentService.getLastStudent();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Student>> getStudentByName(@PathVariable String name) {
        List<Student> students = studentService.getStudentByName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("find-all")
    public ResponseEntity<List<String>> findAllStudentsByAlphabetically() {
        List<String> students = studentService.findAllStudentsByAlphabetically();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/average-age")
    public double getAverageAgeStudent() {
        return studentService.getAverageAge();
    }

    @GetMapping("/print-parallel")
    public void printStudentNames() {
        List<Student> students = studentService.findAllStudents();
        new Thread(() -> {
            if (students.size() >= 2) {
                System.out.println("Main thread: " + students.get(0).getName());
                System.out.println("Main thread: " + students.get(1).getName());
            }
        }).start();


        new Thread(() -> {
            if (students.size() >= 4) {
                System.out.println("Parallel thread 1: " + students.get(2).getName());
                System.out.println("Parallel thread 1: " + students.get(3).getName());
            }
        }).start();


        new Thread(() -> {
            if (students.size() >= 6) {
                System.out.println("Parallel thread 2: " + students.get(4).getName());
                System.out.println("Parallel thread 2: " + students.get(5).getName());
            }
        }).start();


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/students/print-synchronized")
    public void printStudentsSynchronized() {
        List<Student> students = studentService.findAllStudents();
        printStudentNames(students, 0, 2, "Main thread: ");
        new Thread(() -> printStudentNames(students, 2, 4, "Parallel thread 1: ")).start();
        new Thread(() -> printStudentNames(students, 4, 6, "Parallel thread 2: ")).start();
    }

    public void printStudentNames(List<Student> students, int startIndex, int endIndex, String threadName) {
        synchronized (this) {
            for (int i = startIndex; i < endIndex; i++) {
                if (i < students.size()) {
                    System.out.println(threadName + students.get(i).getName());
                }
            }
        }
    }
}
