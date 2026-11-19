package com.spring.spring_rest.service;

import com.spring.spring_rest.dto.StudentDto;
import com.spring.spring_rest.entity.Student;
import com.spring.spring_rest.exception.StudentNotFoundException;
import com.spring.spring_rest.mapper.StudentMapper;
import com.spring.spring_rest.repository.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Implements student management operations using
 * {@link StudentRepository} for persistence.
 */
@Service
public class StudentServiceImplement implements StudentService {

  private StudentRepository studentRepository;

  /**
   * Creates the service with the repository used to store students.
   *
   * @param studentRepository repository for student records
   */
  public StudentServiceImplement(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  /**
   * Creates a student from the supplied data.
   *
   * @param theStudentDto student data to create
   * @return the created student
   */
  @Override
  public StudentDto createStudent(StudentDto theStudentDto) {
    Student theStudent = StudentMapper.mapToStudent(theStudentDto);
    Student saved = studentRepository.save(theStudent);
    return StudentMapper.mapToStudentDto(saved);
  }

  /**
   * Retrieves a student by its identifier.
   *
   * @param theId identifier of the student
   * @return the requested student
   * @throws StudentNotFoundException if no matching student exists
   */
  @Override
  public StudentDto getStudentById(Long theId) {
    Student student = studentRepository.findById(theId)
        .orElseThrow(() -> new StudentNotFoundException(
            "Student id " + theId + " not found!"));

    return StudentMapper.mapToStudentDto(student);
  }

  /**
   * Retrieves all students.
   *
   * @return all stored students
   */
  @Override
  public List<StudentDto> getAllStudents() {
    List<Student> students = studentRepository.findAll();

    return students.stream().map(
        (student) -> StudentMapper.mapToStudentDto(student))
        .collect(Collectors.toList());
  }

  /**
   * Replaces the data of an existing student.
   *
   * @param theId identifier of the student
   * @param theStudentDto replacement student data
   * @return the updated student
   * @throws StudentNotFoundException if no matching student exists
   */
  @Override
  public StudentDto updateStudent(Long theId, StudentDto theStudentDto) {
    Student student = studentRepository.findById(theId)
        .orElseThrow(() -> new StudentNotFoundException(
            "Student id " + theId + " not found!"));

    student.setFirstName(theStudentDto.getFirstName());
    student.setLastName(theStudentDto.getLastName());
    student.setEmail(theStudentDto.getEmail());

    Student saved = studentRepository.save(student);
    return StudentMapper.mapToStudentDto(saved);
  }

  /**
   * Updates the non-null fields of an existing student.
   *
   * @param theId identifier of the student
   * @param theStudentDto fields to update
   * @return the patched student
   * @throws StudentNotFoundException if no matching student exists
   */  
  @Override
  public StudentDto patchStudent(Long theId, StudentDto theStudentDto) {
    Student student = studentRepository.findById(theId)
        .orElseThrow(() -> new StudentNotFoundException(
            "Student id " + theId + " not found!"));

    if (theStudentDto.getFirstName() != null) {
      student.setFirstName(theStudentDto.getFirstName());
    }
    if (theStudentDto.getLastName() != null) {
      student.setLastName(theStudentDto.getLastName());
    }
    if (theStudentDto.getEmail() != null) {
      student.setEmail(theStudentDto.getEmail());
    }

    Student saved = studentRepository.save(student);
    return StudentMapper.mapToStudentDto(saved);
  }
  
  /**
   * Deletes a student by its identifier.
   *
   * @param theId identifier of the student
   * @throws StudentNotFoundException if no matching student exists
   */
  @Override
  public void deleteStudentById(Long theId) {
    studentRepository.findById(theId)
        .orElseThrow(() -> new StudentNotFoundException(
            "Student id " + theId + " not found!"));

    studentRepository.deleteById(theId);
  }
}
