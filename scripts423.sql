SELECT students.name, students.age, faculties.faculty_name
FROM students
         INNER JOIN faculties ON students.faculty_id = faculties.id;

SELECT students.name, students.age
FROM students
         INNER JOIN avatars ON students.id = avatars.student_id;