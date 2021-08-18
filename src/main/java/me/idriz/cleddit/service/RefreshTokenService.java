package me.idriz.cleddit.service;

import java.util.UUID;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.RefreshToken;

public interface RefreshTokenService {
	
	long REFRESH_TOKEN_EXPIRY_TIME_IN_SECONDS = 60 * 120;
	
	UUID generateRefreshToken(Profile profile);
	
	RefreshToken getRefreshToken(UUID id);
	
}
