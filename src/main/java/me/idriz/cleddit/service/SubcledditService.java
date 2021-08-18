package me.idriz.cleddit.service;

import java.util.UUID;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.Subcleddit;

public interface SubcledditService {
	
	Subcleddit findByName(String name);
	
	Subcleddit findById(UUID id);
	
	Subcleddit createSubcleddit(Profile profile, String name, String description);
	
	boolean deleteSubcleddit(UUID subcledditId);
	
}
