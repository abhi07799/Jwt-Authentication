package com.example.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.entity.StudentEnt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//Step 4 ->
@Service
public class JwtService
{
	// Step 5 -> create a secret key
	// or we can set this key inside application properties file and inject in a
	// field using @Value
	// @Value("{security.jwt.secret-key}")
	// private String SECRET_KEY;
	
	private final String SECRET_KEY = "17b3c2cd43338ad0124a7962c405bba1615fae6b4a7cc9da9942ffe1a68bc84d";
	

	
	// Step 6 -> create a method to generate the JWT token and set secret key as sign with
	public String generateToken(StudentEnt student)
	{
		String token = Jwts.builder().subject(student.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).signWith(getSigningKey())
				.compact();

		return token;
	}
	
	//part of step 6
	private SecretKey getSigningKey()
	{
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	//Step 7 -> create a method to extract claims or information from the payload
	//this method is used to extract all the information such as subject, issuedAt or expiration etc from the token we generated
	private Claims extractAllClaims(String token)
	{
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}
	
	//Step 8 -> create a method to extract a specific property or information from the token
	private <T> T extractClaim(String token, Function<Claims, T> resolver)
	{
		Claims claim = extractAllClaims(token);
		return resolver.apply(claim);
	}
	
	//Step 9 -> create a method to extract the username 
	public String extractUsername(String token)
	{
		// we are getting the subject because when we generated the token we put the username in subject parameter
		return extractClaim(token, Claims::getSubject);
	}
	
	//Step 10 -> create a method to validate the token
	// we will check whether the username  from payload and the username from UserDetails are same or not
	public boolean isValid(String token, UserDetails user)
	{
		String username = extractUsername(token);
		if(username.equals(user.getUsername()) && !isTokenExpired(token))
		{
			return true;
		}		
		
		return false;
	}

	//part of step 10 - to check whether token is expired or not
	private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	
	//part of step 10 -> this method is created so that we can get the expiration time from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
	
	
}
