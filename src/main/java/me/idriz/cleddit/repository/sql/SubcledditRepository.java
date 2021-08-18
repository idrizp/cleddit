package me.idriz.cleddit.repository.sql;

import java.util.UUID;
import me.idriz.cleddit.model.Subcleddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcledditRepository extends JpaRepository<Subcleddit, UUID> {

	Subcleddit findByName(String name);
	
	boolean deleteSubcledditById(UUID subcledditId);
}
