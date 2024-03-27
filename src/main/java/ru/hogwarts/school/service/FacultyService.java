package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.*;

@Service
public class FacultyService {
    private Map<Long, Faculty> facultys = new HashMap<>();
        private Long generatedFacultyId = 1L;

        public Faculty createFaculty(Faculty faculty) {
            facultys. put(generatedFacultyId, faculty);
            generatedFacultyId++;
            return faculty;
        }

        public Faculty getFacultyById(Long facultyId) {
            return facultys.get(facultyId);
        }

        public Faculty updateFaculty(Long facultyId, Faculty faculty) {
            facultys.put(generatedFacultyId, faculty);
            return faculty;
        }

        public Faculty deleteFaculty(Long facultyId) {

            return facultys.remove(facultyId);
        }
        public Faculty editFaculty(Faculty faculty) {
            if (!facultys.containsKey(faculty.getId())) {
            return null;
            }
            facultys.put(faculty.getId(), faculty);
            return faculty;
        }
        public Collection<Faculty> findByColor(String color) {
            ArrayList<Faculty> result = new ArrayList<>();
            for (Faculty faculty : facultys.values()) {
                if (Objects.equals(faculty.getColor(), color)) {
                    result.add(faculty);
                }
            }
            return result;
        }



}

