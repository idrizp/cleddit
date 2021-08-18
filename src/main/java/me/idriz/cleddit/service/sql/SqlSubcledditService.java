package me.idriz.cleddit.service.sql;

import java.util.UUID;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.Subcleddit;
import me.idriz.cleddit.repository.sql.SubcledditRepository;
import me.idriz.cleddit.service.SubcledditService;
import org.springframework.stereotype.Service;

@Service
public class SqlSubcledditService implements SubcledditService {
	
	private final SubcledditRepository subcledditRepository;
	
	public SqlSubcledditService(SubcledditRepository subcledditRepository) {
		this.subcledditRepository = subcledditRepository;
	}
	
	@Override
	public Subcleddit findByName(String name) {
		return subcledditRepository.findByName(name.toLowerCase());
	}
	
	@Override
	public Subcleddit findById(UUID id) {
		return subcledditRepository.findById(id).orElse(null);
	}
	
	@Override
	public Subcleddit createSubcleddit(Profile profile, String name, String description) {
		if (findByName(name) != null) {
			return null;
		}
		Subcleddit subcleddit = new Subcleddit(profile, name.toLowerCase(), description);
		return subcledditRepository.save(subcleddit);
	}
	
	@Override
	public boolean deleteSubcleddit(UUID subcledditId) {
		return subcledditRepository.deleteSubcledditById(subcledditId);
	}
}
