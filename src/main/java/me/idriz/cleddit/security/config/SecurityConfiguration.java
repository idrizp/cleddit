package me.idriz.cleddit.security.config;

import javax.servlet.http.HttpServletResponse;
import me.idriz.cleddit.security.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private final TokenFilter tokenFilter;
	
	public SecurityConfiguration(TokenFilter tokenFilter) {
		this.tokenFilter = tokenFilter;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http = http.cors().and().csrf().disable();
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
		http = http
				.exceptionHandling()
				.authenticationEntryPoint(
						(request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized."))
				.and();
		
		http
				.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/comment/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/post/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/subcleddit/**").permitAll()
				.anyRequest().authenticated();
		
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
