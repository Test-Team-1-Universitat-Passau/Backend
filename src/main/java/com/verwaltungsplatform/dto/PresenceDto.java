package com.verwaltungsplatform.dto;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PresenceDto {

	
	@NotNull
	@NotEmpty
	private int affectedUserId;
	
	private Date date;
	
	private int lesson;
	
	private boolean presence;
	
	private boolean confirmation;
	
   

   
	// Default constructor
	public PresenceDto() {
		
	}


	public PresenceDto(@NotNull @NotEmpty int affectedUserId, Date date, boolean presence, int lesson) {
		super();
		this.affectedUserId = affectedUserId;
		this.date = date;
		this.presence = presence;
		this.lesson = lesson;
	}

	

	public PresenceDto(@NotNull @NotEmpty int affectedUserId, Date date, int lesson, boolean presence,
			boolean confirmation) {
		super();
		this.affectedUserId = affectedUserId;
		this.date = date;
		this.lesson = lesson;
		this.presence = presence;
		this.confirmation = confirmation;
	}


	public int getAffectedUserId() {
		return affectedUserId;
	}


	public void setAffectedUserId(int affectedUserId) {
		this.affectedUserId = affectedUserId;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public boolean isPresence() {
		return presence;
	}


	public void setPresence(boolean presence) {
		this.presence = presence;
	}


	public int getLesson() {
		return lesson;
	}


	public void setLesson(int lesson) {
		this.lesson = lesson;
	}


	public boolean isConfirmation() {
		return confirmation;
	}


	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}
	
	
	

	
}
