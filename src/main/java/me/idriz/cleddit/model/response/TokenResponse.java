package me.idriz.cleddit.model.response;

import java.util.UUID;

public class TokenResponse {
	
	private String token;
	private UUID refreshToken;
	
	public TokenResponse(String token, UUID refreshToken) {
		this.token = token;
		this.refreshToken = refreshToken;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public UUID getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(UUID refreshToken) {
		this.refreshToken = refreshToken;
	}
}
