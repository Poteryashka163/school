package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> findByAge(Integer age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int ageMin, int ageMax) {
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public long count() {
        return studentRepository.countAllStudents();
    }

    public int findAverageAge() {
        return studentRepository.getAverageAge();
    }
    public List<Student>getLastStudent(){
        return studentRepository.findLastFiveStudents(5);
    }

    public List<Student>getStudentByName(String name) {
        return studentRepository.getStudentByName(name);
    }

}
