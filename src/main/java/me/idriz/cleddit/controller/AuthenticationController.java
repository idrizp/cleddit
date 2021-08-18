package me.idriz.cleddit.controller;

import java.util.UUID;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.RefreshToken;
import me.idriz.cleddit.model.response.TokenResponse;
import me.idriz.cleddit.service.ProfileService;
import me.idriz.cleddit.service.RefreshTokenService;
import me.idriz.cleddit.util.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	private final PasswordEncoder passwordEncoder;
	private final ProfileService profileService;
	private final RefreshTokenService refreshTokenService;
	private final TokenUtils tokenUtils;
	
	public AuthenticationController(
			PasswordEncoder passwordEncoder,
			ProfileService profileService,
			RefreshTokenService refreshTokenService,
			TokenUtils tokenUtils
	) {
		this.passwordEncoder = passwordEncoder;
		this.profileService = profileService;
		this.refreshTokenService = refreshTokenService;
		this.tokenUtils = tokenUtils;
	}
	
	private TokenResponse generateTokenResponse(Profile profile) {
		String authenticationToken = tokenUtils.sign(profile.getId());
		UUID refreshToken = refreshTokenService.generateRefreshToken(profile);
		return new TokenResponse(authenticationToken, refreshToken);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		Profile profile = profileService.findByUsername(request.username());
		if (profile != null) {
			return ResponseEntity.status(409).build();
		}
		profile = profileService.createProfile(request.username(), request.password(), request.email());
		return ResponseEntity.ok(generateTokenResponse(profile));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		Profile profile = profileService.findByUsername(request.username());
		if (profile == null) {
			return ResponseEntity.status(401).build();
		}
		if (!passwordEncoder.matches(request.password(), profile.getPassword())) {
			return ResponseEntity.status(401).build();
		}
		return ResponseEntity.ok(generateTokenResponse(profile));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
		RefreshToken refreshToken = refreshTokenService.getRefreshToken(refreshRequest.refreshToken());
		if (refreshToken == null) {
			return ResponseEntity.status(401).build();
		}
		return ResponseEntity.ok(new AuthTokenResponse(tokenUtils.sign(refreshToken.getProfile().getId())));
	}
	
	
	record RegisterRequest(String username, String email, String password) {
	
	}
	
	record RefreshRequest(UUID refreshToken) {
	
	}
	
	record LoginRequest(String username, String password) {
	
	}
	
	record AuthTokenResponse(String authToken) {
	
	}
	
}
