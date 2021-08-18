package me.idriz.cleddit.service;

import java.util.List;
import java.util.UUID;
import me.idriz.cleddit.model.Comment;
import me.idriz.cleddit.model.Profile;

public interface CommentService {
	
	Comment findById(UUID id);
	
	Comment createComment(UUID postId, Profile poster, String content);
	
	Comment createReply(UUID commentId, Profile poster, String content);
	
	boolean deleteComment(UUID commentId);
	
	List<Comment> getComments(UUID postId, int page);
}
