package com.example;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseCode;
    private String courseName;
    private List<Student> students;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.students = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void enrollStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void unenrollStudent(Student student) {
        students.remove(student);
    }
    public void clearAllStudents() {
        students.clear();
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public boolean isStudentEnrolled(Student student) {
        return students.contains(student);
    }

    public int getStudentCount() {
        return students.size();
    }
    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students");
            return;
        }
        for (Student student : students) {
            System.out.println("ID: " + student.getId() + ", Name: " + student.getName());
        }
    }
}