package me.idriz.cleddit.security.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.service.ProfileService;
import me.idriz.cleddit.util.AuthenticationUtils;
import me.idriz.cleddit.util.TokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenFilter extends OncePerRequestFilter {
	
	private final TokenUtils tokenUtils;
	private final ProfileService profileService;
	
	public TokenFilter(TokenUtils tokenUtils, ProfileService profileService) {
		this.tokenUtils = tokenUtils;
		this.profileService = profileService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (!TokenUtils.isValidAuthorizationHeader(header)) {
			filterChain.doFilter(request, response);
			return;
		}
		UUID parsed = tokenUtils.parseToken(header.substring(7));
		if (parsed == null) {
			filterChain.doFilter(request, response);
			return;
		}
		Profile profile = profileService.findById(parsed);
		if (profile == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		AuthenticationUtils.authenticateProfile(profile);
		filterChain.doFilter(request, response);
	}
}
