package me.idriz.cleddit.model;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column
	private String content;
	
	@ManyToOne
	private Comment parent;
	
	@ManyToOne
	private Post post;
	
	@ManyToOne
	private Profile poster;
	
	@OneToMany(mappedBy = "parent")
	private Set<Comment> replies;
	
	public Comment() {
	}
	
	public Comment(String content, Comment parent, Post post, Profile poster,
			Set<Comment> replies) {
		this.content = content;
		this.parent = parent;
		this.post = post;
		this.poster = poster;
		this.replies = replies;
	}
	
	public Comment(UUID id, String content, Comment parent, Post post, Profile poster,
			Set<Comment> replies) {
		this.id = id;
		this.content = content;
		this.parent = parent;
		this.post = post;
		this.poster = poster;
		this.replies = replies;
	}
	
	public boolean isReply() {
		return parent != null;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Comment getParent() {
		return parent;
	}
	
	public void setParent(Comment parent) {
		this.parent = parent;
	}
	
	public Post getPost() {
		return post;
	}
	
	public void setPost(Post post) {
		this.post = post;
	}
	
	public Profile getPoster() {
		return poster;
	}
	
	public void setPoster(Profile poster) {
		this.poster = poster;
	}
	
	public Set<Comment> getReplies() {
		return replies;
	}
	
	public void setReplies(Set<Comment> replies) {
		this.replies = replies;
	}
}
