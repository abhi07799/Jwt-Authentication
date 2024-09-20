package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.StudentEnt;
import com.example.model.AuthenticationResponse;
import com.example.service.AuthenticationService;

//Step 13 -> create a controller for login and register/signup
@RestController
public class AuthenticationController
{
	@Autowired
	private AuthenticationService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody StudentEnt request)
	{
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody StudentEnt request)
	{
		return ResponseEntity.ok(authService.authenticate(request));
	}
}
