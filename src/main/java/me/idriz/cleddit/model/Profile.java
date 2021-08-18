package me.idriz.cleddit.model;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Profile {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column
	private String username;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column
	private boolean verifiedEmail;
	
	@OneToMany(mappedBy = "profile")
	private Set<Vote> votes;
	
	@OneToMany(mappedBy = "poster")
	private Set<Post> posts;
	
	@OneToMany(mappedBy = "poster")
	private Set<Comment> comments;
	
	@OneToMany(mappedBy = "administrator")
	private Set<Subcleddit> subcleddits;
	
	public Profile() {
	}
	
	public Profile(UUID id, String username, String email, String password, boolean verifiedEmail, Set<Vote> votes,
			Set<Post> posts, Set<Comment> comments, Set<Subcleddit> subcleddits) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.verifiedEmail = verifiedEmail;
		this.votes = votes;
		this.posts = posts;
		this.comments = comments;
		this.subcleddits = subcleddits;
	}
	
	public Profile(String username, String email, String password, boolean verifiedEmail, Set<Vote> votes, Set<Post> posts,
			Set<Comment> comments, Set<Subcleddit> subcleddits) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.verifiedEmail = verifiedEmail;
		this.votes = votes;
		this.posts = posts;
		this.comments = comments;
		this.subcleddits = subcleddits;
	}
	
	public Set<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public Set<Subcleddit> getSubcleddits() {
		return subcleddits;
	}
	
	public void setSubcleddits(Set<Subcleddit> subcleddits) {
		this.subcleddits = subcleddits;
	}
	
	public Set<Vote> getVotes() {
		return votes;
	}
	
	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}
	
	public Set<Post> getPosts() {
		return posts;
	}
	
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isVerifiedEmail() {
		return verifiedEmail;
	}
	
	public void setVerifiedEmail(boolean verifiedEmail) {
		this.verifiedEmail = verifiedEmail;
	}
}
