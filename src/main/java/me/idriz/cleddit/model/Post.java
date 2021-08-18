package me.idriz.cleddit.model;

import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Post {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Subcleddit subcleddit;
	
	@ManyToOne
	private Profile poster;
	
	@OneToMany(mappedBy = "post")
	private Set<Vote> votes;
	
	@OneToMany(mappedBy = "post")
	private Set<Comment> comments;
	
	public Post() {
	}
	
	public Post(UUID id, String title, String content, Profile poster, Set<Vote> votes, Set<Comment> comments,
			Subcleddit subcleddit) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.poster = poster;
		this.votes = votes;
		this.comments = comments;
		this.subcleddit = subcleddit;
	}
	
	public Post(String title, String content, Profile poster, Set<Vote> votes, Set<Comment> comments,
			Subcleddit subcleddit) {
		this.title = title;
		this.content = content;
		this.poster = poster;
		this.votes = votes;
		this.comments = comments;
		this.subcleddit = subcleddit;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Profile getPoster() {
		return poster;
	}
	
	public void setPoster(Profile poster) {
		this.poster = poster;
	}
	
	public Set<Vote> getVotes() {
		return votes;
	}
	
	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}
	
	public Subcleddit getSubcleddit() {
		return subcleddit;
	}
	
	public void setSubcleddit(Subcleddit subcleddit) {
		this.subcleddit = subcleddit;
	}
	
	public Set<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public int getUpvotes() {
		return (int) votes.stream().filter(Vote::isPositive).count();
	}
	
	public int getDownvotes() {
		return (int) votes.stream().filter(vote -> !vote.isPositive()).count();
	}
}
