package me.idriz.cleddit.repository.sql;

import java.util.UUID;
import me.idriz.cleddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
	
	Vote findByProfileIdAndPostId(UUID profileId, UUID postId);
	
}
