package me.idriz.cleddit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
	
	public static final long EXPIRE_TIME_IN_SECONDS = 1800;
	
	private final Key secretKey;
	private final JwtParser jwtParser;
	
	public TokenUtils(@Value("${cleddit.jwt.secret_key}") String secretKey) {
		this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
		this.jwtParser = Jwts.parserBuilder().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).build();
	}
	
	public static boolean isValidAuthorizationHeader(String authorizationHeader) {
		if (authorizationHeader == null) {
			return false;
		}
		// Bearer_ (_ is a space)
		String token = authorizationHeader.substring(7);
		return !token.isEmpty();
	}
	
	public String sign(UUID userId) {
		return Jwts.builder()
				.claim("id", userId.toString())
				.setIssuer("cleddit")
				.setSubject(userId.toString())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME_IN_SECONDS * 1000))
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public UUID parseToken(String token) {
		try {
			Jws<Claims> claims = jwtParser.parseClaimsJws(token);
			return UUID.fromString(claims.getBody().get("id", String.class));
		} catch (JwtException exception) {
			return null;
		}
	}
	
}
