package me.idriz.cleddit.service.sql;

import java.util.UUID;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.RefreshToken;
import me.idriz.cleddit.repository.sql.RefreshTokenRepository;
import me.idriz.cleddit.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class SqlRefreshTokenService implements RefreshTokenService {
	
	private final RefreshTokenRepository repository;
	
	public SqlRefreshTokenService(RefreshTokenRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UUID generateRefreshToken(Profile profile) {
		RefreshToken refreshToken = new RefreshToken(profile);
		repository.save(refreshToken);
		return refreshToken.getId();
	}
	
	@Override
	public RefreshToken getRefreshToken(UUID id) {
		RefreshToken found = repository.findById(id).orElse(null);
		if (found == null) {
			return null;
		}
		if (System.currentTimeMillis() > found.getCreatedTimestamp() + REFRESH_TOKEN_EXPIRY_TIME_IN_SECONDS * 1000) {
			return null;
		}
		return found;
	}
}
