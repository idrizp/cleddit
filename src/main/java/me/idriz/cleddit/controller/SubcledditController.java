package me.idriz.cleddit.controller;

import static me.idriz.cleddit.util.AuthenticationUtils.getAuthenticatedProfile;

import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.Subcleddit;
import me.idriz.cleddit.service.SubcledditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subcleddit")
public class SubcledditController {
	
	private final SubcledditService service;
	
	public SubcledditController(SubcledditService service) {
		this.service = service;
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createSubcleddit(@RequestBody SubcledditCreateRequest request) {
		if (request.name().equalsIgnoreCase("all")) {
			return ResponseEntity.badRequest().build();
		}
		Profile profile = getAuthenticatedProfile();
		
		Subcleddit subcleddit = service.createSubcleddit(profile, request.name(), request.description());
		if (subcleddit == null) {
			return ResponseEntity.status(409).build();
		}
		
		return ResponseEntity.status(200).build();
	}
	
	@DeleteMapping("/{name}")
	public ResponseEntity<?> deleteSubcleddit(@PathVariable String name) {
		Profile profile = getAuthenticatedProfile();
		Subcleddit subcleddit = service.findByName(name.toLowerCase());
		if (!subcleddit.getAdministrator().getId().equals(profile.getId())) {
			return ResponseEntity
					.status(403)
					.build();
		}
		if (service.deleteSubcleddit(subcleddit.getId())) {
			return ResponseEntity.status(200).build();
		}
		return ResponseEntity.status(500).build();
	}
	
	record SubcledditCreateRequest(String name, String description) {
	
	}
	
}
