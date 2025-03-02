package com.ProjectManagement.Application.Config;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	static SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	
	public static String generateToken(Authentication auth) {
		
		String jwt=Jwts.builder().setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+60*60*60*24))
				.claim("email", auth.getName())
				.signWith(key)
				.compact();
			
		return jwt;
	} 
	
	public static String getEmailFromToken(String jwt) {
		//Bearer+Token => We need to remove first 7 characters of JWTtoken inorder to get the actual token
		
		jwt=jwt.substring(7);
		
		Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email=String.valueOf(claims.get("email"));
		return email;
	}
}
