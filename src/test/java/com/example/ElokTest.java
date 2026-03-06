package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ElokTest {

	private Course course;
	private List<Student> students;

	@BeforeAll
	void initClass() {
		course = new Course("CS101", "Software Testing");
		students = new ArrayList<>();
	}

	@AfterAll
	void cleanClass() {
		course = null;
		students = null;
	}

	@BeforeEach
	void initMethod() {
		for (int i = 0; i < 5; i++) {
			String id = "S" + (students.size() + 1);
			String name = "Student " + (students.size() + 1);
			Student student = new Student(id, name);
			students.add(student);
			course.enrollStudent(student);
		}
	}

	@AfterEach
	void cleanMethod() {
		students.clear();
		course.clearAllStudents();
	}

	@Test
	void testMethod1() {
		assertTrue(course.isStudentEnrolled(students.get(0)));
		assertEquals(5, course.getStudentCount());
	}

	@Test
	void testMethod2() {
		course.unenrollStudent(students.get(0));
		assertFalse(course.isStudentEnrolled(students.get(0)));
		assertEquals(4, course.getStudentCount());
	}

}
