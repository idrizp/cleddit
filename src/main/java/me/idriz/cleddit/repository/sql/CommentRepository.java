package me.idriz.cleddit.repository.sql;

import java.util.UUID;
import me.idriz.cleddit.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
	
	Comment deleteCommentById(UUID commentId);
	
	Page<Comment> findCommentsByPostId(UUID postId, Pageable pageable);
	
}
