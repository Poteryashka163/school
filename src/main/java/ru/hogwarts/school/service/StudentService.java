package ru.hogwarts.school.service;

import liquibase.pro.packaged.L;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;


@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student.");
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        logger.info("Was invoked method for call student.");
        return studentRepository.findById(studentId).get();
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update profile student.");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        logger.info("Was invoked method for delete student.");
        studentRepository.deleteById(studentId);
    }

    public List<Student> findByAge(Integer age) {
        logger.info("Was invoked method for find student by age.");
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int ageMin, int ageMax) {
        logger.info("Was invoked method for find student by between 2 age.");
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public Optional<Student> findById(Long id) {
        logger.info("Was invoked method for find student by id.");
        return studentRepository.findById(id);
    }

    public long count() {
        logger.info("Was invoked method for count students.");
        return studentRepository.countAllStudents();
    }

    public int findAverageAge() {
        logger.info("Was invoked method for find average age.");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastStudent() {
        logger.info("Was invoked method for get last student.");
        return studentRepository.findLastFiveStudents(5);
    }

    public List<Student> getStudentByName(String name) {
        logger.info("Was invoked method for get student by name.");
        return studentRepository.getStudentByName(name);
    }

    public List<String> findAllStudentsByAlphabetically() {
        logger.info("Was invoked method for find all students by alphabetically.");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.toUpperCase().startsWith("A"))
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public double getAverageAge() {
        List<Student> students = studentRepository.findAll();
        OptionalDouble average = students.stream()
                .mapToInt(Student::getAge)
                .average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }


    public void printStudentsName() {
        List<Student> students = studentRepository.findAll();
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

    public void printStudentsNamesSynchronized() {
        List<Student> students = studentRepository.findAll();
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


