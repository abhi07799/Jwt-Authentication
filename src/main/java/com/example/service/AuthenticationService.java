package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.StudentEnt;
import com.example.model.AuthenticationResponse;
import com.example.repository.StudentRepo;
import com.example.security.JwtService;

//Step 14 -> create a service class to handle authentication
@Service
public class AuthenticationService
{	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private StudentRepo repo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(StudentEnt request)
	{
		StudentEnt student = new StudentEnt();
		
		student.setFullName(request.getFullName());
		student.setEmail(request.getEmail());
		student.setUsername(request.getUsername());
		student.setPassword(passwordEncoder.encode(request.getPassword()));
		student.setRole(request.getRole());
		
		student  = repo.save(student);
		
		String token = jwtService.generateToken(student);
		
		return new AuthenticationResponse(token);
	}
	
	public AuthenticationResponse authenticate(StudentEnt request)
	{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		StudentEnt student =repo.findByUsername(request.getUsername()).orElseThrow();
		
		String token = jwtService.generateToken(student);
		
		return new AuthenticationResponse(token);
	}
}
