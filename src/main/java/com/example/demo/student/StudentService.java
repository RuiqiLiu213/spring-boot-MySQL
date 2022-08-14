package com.example.demo.student;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentService {
	private final StudentDao studentDao;
	@Autowired
	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	public List<Student> getStudents(){
		// return a List
		return studentDao.findAll();
	}
	
	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = studentDao.findStudentByEmail(student.getEmail());
		if (studentOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		studentDao.save(student);
	}
	
	public void deleteStudent(Long id) {
		boolean exists = studentDao.existsById(id);
		if (!exists) {
			throw new IllegalStateException(id+ "does not exists");
		}
		studentDao.deleteById(id);;
	}
	
	@Transactional
	public void updateStudent(Long id, String name, String email) {
		Student student = studentDao.findById(id)
				.orElseThrow(()-> new IllegalStateException(id+ "does not exists"));
		if(name != null && name.length()>0 && !Objects.equals(student.getName(), name)) {
			student.setName(name);
		}
		if(email != null && email.length()>0 && !Objects.equals(student.getEmail(), email)) {
			Optional<Student> studentOptional = studentDao.findStudentByEmail(student.getEmail());
			if (studentOptional.isPresent()) {
				throw new IllegalStateException("email taken");
			}
			student.setEmail(email);
		}
	}
}
