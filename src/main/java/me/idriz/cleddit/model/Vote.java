package me.idriz.cleddit.model;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vote {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	private Profile profile;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Post post;
	
	@Column
	private boolean positive;
	
	public Vote() {
	}
	
	public Vote(UUID id, Profile profile, Post post, boolean positive) {
		this.id = id;
		this.profile = profile;
		this.post = post;
		this.positive = positive;
	}
	
	public Vote(Profile profile, Post post, boolean positive) {
		this.profile = profile;
		this.post = post;
		this.positive = positive;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public Post getPost() {
		return post;
	}
	
	public void setPost(Post post) {
		this.post = post;
	}
	
	public boolean isPositive() {
		return positive;
	}
	
	public void setPositive(boolean positive) {
		this.positive = positive;
	}
}
