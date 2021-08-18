package me.idriz.cleddit.repository.sql;

import java.util.UUID;
import me.idriz.cleddit.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
	
	Profile findByUsernameContainingIgnoreCase(String username);
}
