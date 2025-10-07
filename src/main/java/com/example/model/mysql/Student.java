package com.example.model.mysql;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rollNo;
    private String name;
    private String subject;
    private String marks;
    private String address;
    private int age;

    public Student() {
    }

    public Student(int rollNo, String name, String subject, String marks, String address, int age) {
        this.rollNo = rollNo;
        this.name = name;
        this.subject = subject;
        this.marks = marks;
        this.address = address;
        this.age = age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", marks='" + marks + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}