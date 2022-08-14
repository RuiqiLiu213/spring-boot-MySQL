package com.example.demo.student;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Working with JPA
// Responsible for Data Access
@Repository
public interface StudentDao extends JpaRepository<Student, Long>{
	
	// Custom Finding
	Optional<Student> findStudentByEmail(String email);
	
}
