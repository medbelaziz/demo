package com.example.security;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

	private AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/token")
	public ResponseEntity<Map<String, String>> jwToken(String grantType, String username, String password,
			boolean withRefrechToken, String refreshToken) {

		Map<String, String> map = new HashMap<>();
		try {
			map = authenticationService.authenticate(grantType, username, password, withRefrechToken, refreshToken);

		} catch (Exception e1) {
			return new ResponseEntity<>(Map.of("Error Message", e1.getMessage()), HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping("/datatest")
	@PreAuthorize("hasAuthority('SCOPE_USER')") // pour que ça march il faut ajouter l 'annotation
												// @EnableGlobalMethodSecurity(prePostEnabled = true)
	public Map<String, String> dataTest(Authentication authentication) {

		return Map.of("Message", "Data test", "Username", authentication.getName(), "Scope", authentication
				.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" ")));
	}

	@PostMapping("/datatest")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public Map<String, String> saveDataTest(String data, Authentication authentication) {

		return Map.of("Saved Data", data, "Username", authentication.getName(), "Scope", authentication.getAuthorities()
				.stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" ")));
	}
}
