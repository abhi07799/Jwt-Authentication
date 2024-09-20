package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.StudentEnt;

@Repository
public interface StudentRepo extends JpaRepository<StudentEnt, Long>
{
	//Step 2 -> create a repository and create this below method 
	Optional<StudentEnt> findByUsername(String username);
}
