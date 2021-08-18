package me.idriz.cleddit.service;

import java.util.UUID;
import me.idriz.cleddit.model.Profile;

public interface ProfileService {
	
	Profile createProfile(String username, String password, String email);
	
	Profile findById(UUID id);
	
	Profile findByUsername(String username);
	
}
