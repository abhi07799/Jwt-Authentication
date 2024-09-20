package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController
{
	@GetMapping("test")
	public String test()
	{
		return "Jai Bajrang Bali";
	}

	@GetMapping("/public")
	public ResponseEntity<String> demo()
	{
		return ResponseEntity.ok("Hello from secured url");
	}

	@GetMapping("/admin")
	public ResponseEntity<String> adminOnly()
	{
		return ResponseEntity.ok("Hello from admin only url");
	}
	
	@GetMapping("/student")
	public ResponseEntity<String> studentOnly()
	{
		return ResponseEntity.ok("student only url");
	}
}
