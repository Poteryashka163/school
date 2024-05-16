package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;


import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);

    List<Student> findByAgeBetween(int ageMin, int ageMax);

    @Query(value = "SELECT count (*) FROM Student", nativeQuery = true)
    Long countAllStudents();

    @Query(value = "SELECT AVG(age) FROM Student", nativeQuery = true)
    Integer getAverageAge();

    @Query(value = "SELECT '*' FROM student ORDER BY id DESC limit :quantity", nativeQuery = true)
    List<Student> findLastFiveStudents(@Param("quantity") int quantity);

    List<Student>getStudentByName(String name);


}
