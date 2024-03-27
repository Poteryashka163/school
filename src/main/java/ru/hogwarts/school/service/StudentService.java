package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private Map<Long, Student> students = new HashMap<>();
        private Long generatedStudentId = 1L;

        public Student createStudent(Student student) {
            students. put(generatedStudentId, student);
            generatedStudentId++;
            return student;
        }

        public Student getStudentById(Long studentId) {
            return students.get(studentId);
        }

        public Student updateStudent(Long studentId, Student student) {
            students.put(generatedStudentId, student);
            return student;
        }

        public Student deleteStudent(Long studentId) {
            return students.remove(studentId);
        }

        public Student editFaculty(Student student) {
            if (!students.containsKey(student.getId())) {
            return null;
            }
            students.put(student.getId(), student);
            return student;
    }
        public Collection<Student> findByAge(int age) {
            ArrayList<Student> result = new ArrayList<>();
            for (Student student : students.values()) {
                if (student.getAge() == age) {
                result.add(student);
                }
            }
             return result;
    }


}
