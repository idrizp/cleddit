package me.idriz.cleddit.model.response;

import java.util.UUID;
import me.idriz.cleddit.model.Comment;

public class CommentResponse {
	
	private UUID id;
	
	private UUID posterId;
	private String posterName;
	
	private String content;
	
	public CommentResponse(Comment comment) {
		this.id = comment.getId();
		this.content = comment.getContent();
		if (comment.getPoster() != null) {
			this.posterName = comment.getPoster().getUsername();
			this.posterId = comment.getPost().getId();
		}
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID getPosterId() {
		return posterId;
	}
	
	public void setPosterId(UUID posterId) {
		this.posterId = posterId;
	}
	
	public String getPosterName() {
		return posterName;
	}
	
	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
