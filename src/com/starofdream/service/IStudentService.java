package com.starofdream.service;

import java.util.List;

import com.starofdream.entity.Student;

public interface IStudentService {

	public Student queryStudentBySno(int sno);

	public List<Student> queryAllStudents();

	public boolean updateStudentBySno(int sno, Student student);

	public boolean deleteStudentBySno(int sno);

	public boolean addStudent(Student student);
	
	public int getTotalCount();
	
	public List<Student> queryStudentsByPage(int currentPage, int pageSize);
}
