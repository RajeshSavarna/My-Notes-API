package com.mynotes.api.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mynotes.api.model.UserData;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtilsClass {
	
	@Value("${app.jwt.secretkey}")
	private String secretKey;

	@Value("${app.auth.ttl}")
	private long ttl;
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ttl))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public String generateToken(UserData userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims)
				.setSubject(userDetails.getEmail())
				.claim("userId", userDetails.getId())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ttl))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		return claimsResolver.apply(claims);
	}

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public String extractUserId(String token) {
		final Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		return claims.get("userId").toString();
	}

	public Boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

}
