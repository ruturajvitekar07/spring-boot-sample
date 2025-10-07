package com.example.controller;

import com.example.model.mysql.Student;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/hi")
    public String hello(){
        return "Hello from Student";
    }

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student){
        return service.addStudent(student);
    }

    @GetMapping("/get/{rollNo}")
    public Student getStudent(@PathVariable int rollNo){
        return service.getStudent(rollNo);
    }

    @GetMapping("/get")
    public List<Student> getAllStudents(@RequestParam(value = "grid-column", required = false) String column,
                                        @RequestParam(value = "grid-dir", required = false) Integer direction,
                                        @RequestParam(value = "grid-filter", required = false) String filter){
        return service.getAllStudents(column, direction, filter);
    }

    @DeleteMapping("/delete/{rollNo}")
    public boolean deleteStudent(@PathVariable int rollNo){
        return service.deleteStudent(rollNo);
    }
}