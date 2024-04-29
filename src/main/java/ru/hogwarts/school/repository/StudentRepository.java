package ru.hogwarts.school.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;



import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository <Student, Long> {
    List <Student> findByAge (Integer age);
    List<Student>findByAgeBetween(int ageMin ,int ageMax);
    @Query("SELECT count (*) FROM Student")
    Integer countAllStudents();
    @Query("SELECT AVG(age) FROM Student")
    Integer getAverageAge();
    @Query(value = "SELECT s FROM Student s ORDER BY s.id DESC")
    Page<Student> findLastFiveStudents(Pageable pageable);
}
