package me.idriz.cleddit.service.sql;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import me.idriz.cleddit.model.Comment;
import me.idriz.cleddit.model.Post;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.repository.sql.CommentRepository;
import me.idriz.cleddit.service.CommentService;
import me.idriz.cleddit.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SqlCommentService implements CommentService {
	
	private final CommentRepository commentRepository;
	private final PostService postService;
	
	public SqlCommentService(CommentRepository commentRepository, PostService postService) {
		this.commentRepository = commentRepository;
		this.postService = postService;
	}
	
	@Override
	public Comment findById(UUID id) {
		return commentRepository.findById(id).orElse(null);
	}
	
	@Override
	public Comment createComment(UUID postId, Profile poster, String content) {
		Post post = postService.getPostById(postId);
		if (post == null) {
			return null;
		}
		Comment comment = new Comment(content, null, post, poster, new HashSet<>());
		return commentRepository.save(comment);
	}
	
	@Override
	public Comment createReply(UUID commentId, Profile poster, String content) {
		Comment parent = commentRepository.findById(commentId).orElse(null);
		if (parent == null) {
			return null;
		}
		Comment comment = new Comment(content, parent, parent.getPost(), poster, new HashSet<>());
		return commentRepository.save(comment);
	}
	
	@Override
	public boolean deleteComment(UUID commentId) {
		return commentRepository.deleteCommentById(commentId) != null;
	}
	
	@Override
	public List<Comment> getComments(UUID postId, int page) {
		return commentRepository.findCommentsByPostId(postId, Pageable.ofSize(20).withPage(page)).getContent();
	}
}
