package com.translineindia.vms.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtHelper {

//	@Value("${app.mode}")
	private String appMode;

	public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

	// public static final long JWT_TOKEN_VALIDITY = 60;
	private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(UserPrincipal userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	// generate token for user
	public String generateToken(String usernameToken) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, usernameToken);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + getTokenValidity() * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public long getTokenValidity() {
		if (appMode == null || appMode.equalsIgnoreCase("development")) {
			return JWT_TOKEN_VALIDITY * 30;
		}
		return JWT_TOKEN_VALIDITY;
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		UserPrincipal user = (UserPrincipal) userDetails;
		String usernameToken = user.getCmpCd() + "::" + user.getUsername();
		return (username.equals(user.getUsername()) || username.equals(usernameToken)) && !isTokenExpired(token);
	}

	// validate token
	public Boolean validateToken(String token, String usernameToken) {
		final String username = getUsernameFromToken(token);
		return username.equals(usernameToken) && !isTokenExpired(token);
	}

//	// validate token
//	public Boolean validateToken(String token, String userId, String cmpCd) {
//		final String username = getUsernameFromToken(token);
//		return (username.equals(userId+"$"+userId) && !isTokenExpired(token));
//	}

}