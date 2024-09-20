package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.repository.StudentRepo;


//Step 3 -> create a class which implements UserDetailsService interface
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	private StudentRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		// TODO Auto-generated method stub
		return repo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User : "+username+" not Found"));
	}

}
