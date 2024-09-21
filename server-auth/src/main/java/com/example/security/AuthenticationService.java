package com.example.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private UserDetailsService userDetailsService;
	private JwtEncoder jwtEncoder;
	private JwtDecoder jwtDecoder;
	private AuthenticationManager autheticationManager;

	public AuthenticationService(UserDetailsService userDetailsService, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder,
			AuthenticationManager autheticationManager) {
		this.userDetailsService = userDetailsService;
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
		this.autheticationManager = autheticationManager;
	}

	public Map<String, String> authenticate(String grantType, String username, String password,
			boolean withRefrechToken, String refreshToken) throws Exception {

		Instant instant = Instant.now();
		Map<String, String> map = new HashMap<>();
		String subject = null;
		String scope = null;

		if (grantType.equals("password")) {
			Authentication authentication = autheticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			scope = authentication.getAuthorities().stream().map(author -> author.getAuthority())
					.collect(Collectors.joining(" "));
			subject = authentication.getName();
		} else if (grantType.equals("refreshToken")) {
			if (refreshToken == null) {
				throw new Exception("refreshToken required");
			}
			Jwt jwt = jwtDecoder.decode(refreshToken);
			String userName = jwt.getSubject();
			UserDetails user = userDetailsService.loadUserByUsername(userName);
			scope = user.getAuthorities().stream().map(author -> author.getAuthority())
					.collect(Collectors.joining(" "));
			subject = jwt.getSubject();
		}

		// generate accessToken
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder().subject(subject).issuedAt(instant)
				.expiresAt(instant.plus(withRefrechToken ? 5 : 10, ChronoUnit.MINUTES)).issuer("SUCURITY-SERVICE")
				.claim("scope", scope).build();

		String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
		map.put("jwtAccessToken", jwtAccessToken);

		// generate RefreshToken
		if (withRefrechToken) {
			JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder().subject(subject).issuedAt(instant)
					.expiresAt(instant.plus(30, ChronoUnit.MINUTES)).issuer("SUCURITY-SERVICE").build();

			String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtRefreshClaimsSet)).getTokenValue();
			map.put("jwtRefreshToken", jwtRefreshToken);
		}
		return map;

	}

}
