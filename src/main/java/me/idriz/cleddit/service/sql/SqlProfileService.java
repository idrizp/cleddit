package me.idriz.cleddit.service.sql;

import java.util.HashSet;
import java.util.UUID;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.repository.sql.ProfileRepository;
import me.idriz.cleddit.service.ProfileService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SqlProfileService implements ProfileService {
	
	private final ProfileRepository profileRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SqlProfileService(ProfileRepository profileRepository,
			PasswordEncoder passwordEncoder) {
		this.profileRepository = profileRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Profile createProfile(String username, String password, String email) {
		return profileRepository.save(
				new Profile(username, email, passwordEncoder.encode(password), false, new HashSet<>(), new HashSet<>(), new HashSet<>(),
						new HashSet<>()));
	}
	
	@Override
	public Profile findById(UUID id) {
		return profileRepository.findById(id).orElse(null);
	}
	
	@Override
	public Profile findByUsername(String username) {
		return profileRepository.findByUsernameContainingIgnoreCase(username);
	}
}
