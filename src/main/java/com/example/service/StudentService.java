package com.example.service;

import com.example.exception.StudentNotFoundException;
import com.example.model.mysql.Student;
import com.example.repo.mysql.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student addStudent(Student student){

//        if (repository.findById(student.getRollNo()).isPresent()){
//            throw new StudentAlreadyPresentException("Student already exists with rollNo: " + student.getRollNo());
//        }
        return repository.save(student);
    }

    public Student getStudent(int rollNo){
        return repository.findById(rollNo).
                orElseThrow(() -> new StudentNotFoundException("Student not found with rollNo: " + rollNo));
    }

    public List<Student> getAllStudents(){
        return repository.findAll();
    }

    public boolean deleteStudent(int rollNo){
        Student result = repository.findById(rollNo).
                orElseThrow(() -> new StudentNotFoundException("Student not deleted, as student with rollNo: " + rollNo+ " does not exist "));
        if (result == null){
            return false;
        }
        repository.delete(result);
        return true;
    }

//    public List<Student> getAllStudents(String column, Integer direction) {
//        // Default sort by id ascending if params are null
//        Comparator<Student> comparator = Comparator.comparing(Student::getRollNo);
//
//        // Apply dynamic sorting based on request param
//        if (column != null && !column.isBlank()) {
//            comparator = switch (column.toLowerCase()) {
//                case "name" -> Comparator.comparing(Student::getName, Comparator.nullsLast(String::compareToIgnoreCase));
//                case "subject" -> Comparator.comparing(Student::getSubject, Comparator.nullsLast(String::compareToIgnoreCase));
//                case "marks" -> Comparator.comparing(Student::getMarks, Comparator.nullsLast(String::compareToIgnoreCase));
//                case "address" -> Comparator.comparing(Student::getAddress, Comparator.nullsLast(String::compareToIgnoreCase));
//                case "age" -> Comparator.comparing(Student::getAge);
//                default -> Comparator.comparing(Student::getRollNo);
//            };
//        }
//
//        // Reverse order if direction == 1
//        if (direction != null && direction == 1) {
//            comparator = comparator.reversed();
//        }
//
//        return repository.findAll().stream()
//                .sorted(comparator)
//                .toList();
//    }
    
    public List<Student> getAllStudents(String column, Integer direction, String filter) {
        String sortColumn = (column == null || column.isBlank()) ? "rollno" : column.toLowerCase();
        boolean descending = direction != null && direction == 1;

        return repository.findAll().stream()
                // Apply filter if provided
                .filter(student -> {
                    if (filter == null || filter.isBlank()) return true;

                    String[] parts = filter.split("_", 3);
                    if (parts.length < 2) return true; // invalid filter, ignore

                    String filterColumn = parts[0].toLowerCase();
                    int filterOption;
                    try {
                        filterOption = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        return true; // invalid option, ignore
                    }

                    String filterValue = parts.length == 3 ? parts[2] : "";

                    return applyFilter(student, filterColumn, filterOption, filterValue);
                })
                // Apply sorting
                .sorted(getComparator(sortColumn, descending))
                .toList();
    }

    private boolean applyFilter(Student student, String column, int option, String value) {
        switch (column) {
//            case "rollno" -> {
//                int roll = student.getRollNo() ? student.getRollNo().toString() : "";
//                return matchString(roll, option, value);
//            }
            case "name" -> {
                String name = student.getName() != null ? student.getName() : "";
                return matchString(name, option, value);
            }
            case "subject" -> {
                String subject = student.getSubject() != null ? student.getSubject() : "";
                return matchString(subject, option, value);
            }
            case "marks" -> {
                String marks = student.getMarks() != null ? student.getMarks().toString() : "";
                return matchString(marks, option, value);
            }
            case "address" -> {
                String address = student.getAddress() != null ? student.getAddress() : "";
                return matchString(address, option, value);
            }
//            case "age" -> {
//                String age = student.getAge() != null ? student.getAge().toString() : "";
//                return matchString(age, option, value);
//            }
            default -> {
                return true;
            }
        }
    }

    private boolean matchString(String fieldValue, int option, String filterValue) {
        return switch (option) {
            case 1 -> fieldValue.equalsIgnoreCase(filterValue);        // equals
            case 2 -> fieldValue.toLowerCase().contains(filterValue); // contains
            case 3 -> fieldValue.toLowerCase().startsWith(filterValue); // startsWith
            case 4 -> fieldValue.toLowerCase().endsWith(filterValue);   // endsWith
            case 5 -> fieldValue.isBlank(); // isBlank
            default -> true; // unknown option, ignore
        };
    }

    private Comparator<Student> getComparator(String column, boolean descending) {
        Comparator<Student> comparator = switch (column) {
            case "name" -> Comparator.comparing(Student::getName, Comparator.nullsLast(String::compareToIgnoreCase));
            case "subject" -> Comparator.comparing(Student::getSubject, Comparator.nullsLast(String::compareToIgnoreCase));
            case "marks" -> Comparator.comparing(Student::getMarks, Comparator.nullsLast(String::compareToIgnoreCase));
            case "address" -> Comparator.comparing(Student::getAddress, Comparator.nullsLast(String::compareToIgnoreCase));
            case "age" -> Comparator.comparing(Student::getAge, Comparator.nullsLast(Integer::compareTo));
            default -> Comparator.comparing(Student::getRollNo, Comparator.nullsLast(Integer::compareTo));
        };
        return descending ? comparator.reversed() : comparator;
    }
}