package me.idriz.cleddit.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class RefreshToken {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	private Profile profile;
	
	@CreatedDate
	private long createdTimestamp;
	
	public RefreshToken() {
	}
	
	public RefreshToken(UUID id, Profile profile) {
		this.id = id;
		this.profile = profile;
	}
	
	public RefreshToken(Profile profile) {
		this.profile = profile;
	}
	
	public long getCreatedTimestamp() {
		return createdTimestamp;
	}
	
	public void setCreatedTimestamp(long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
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
}
