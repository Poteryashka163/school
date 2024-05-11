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
    public List<Student>getLastStudent(){
        logger.info("Was invoked method for get last student.");
        return studentRepository.findLastFiveStudents(5);
    }

    public List<Student>getStudentByName(String name) {
        logger.info("Was invoked method for get student by name.");
        return studentRepository.getStudentByName(name);
    }

    public List<String>findAllStudentsByAlphabetically() {
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
}
