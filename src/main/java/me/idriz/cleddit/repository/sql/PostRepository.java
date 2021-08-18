package me.idriz.cleddit.repository.sql;

import java.util.UUID;
import me.idriz.cleddit.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
	Post deletePostById(UUID postId);
	Page<Post> findBySubcledditName(String subcledditName, Pageable pageable);
}
