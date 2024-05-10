package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;


@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty.");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        logger.info("Was invoked method for get faculty by id.");
        return facultyRepository.findById(facultyId).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty.");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        logger.info("Was invoked method for delete faculty.");
        facultyRepository.deleteById(facultyId);
    }

    public List<Faculty> findByColor(String color) {
        logger.info("Was invoked method for find facylty by color.");
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for find faculty by name or color.");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Optional<Faculty> findById(Long id) {
        logger.info("Was invoked method for find faculty by id.");
        return facultyRepository.findById(id);
    }
}


