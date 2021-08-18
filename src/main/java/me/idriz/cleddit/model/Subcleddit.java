package me.idriz.cleddit.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Subcleddit {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	private Profile administrator;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	public Subcleddit() {
	}
	
	public Subcleddit(UUID id, Profile administrator, String name, String description) {
		this.id = id;
		this.administrator = administrator;
		this.name = name;
		this.description = description;
	}
	
	public Subcleddit(Profile administrator, String name, String description) {
		this.administrator = administrator;
		this.name = name;
		this.description = description;
	}
	
	public Profile getAdministrator() {
		return administrator;
	}
	
	public void setAdministrator(Profile administrator) {
		this.administrator = administrator;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
