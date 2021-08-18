package me.idriz.cleddit.util;

import java.util.Collections;
import me.idriz.cleddit.model.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {
	
	public static Profile getAuthenticatedProfile() {
		return (Profile) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
	}
	
	public static void authenticateProfile(Profile profile) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(profile, null,
				Collections.emptyList());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
}
