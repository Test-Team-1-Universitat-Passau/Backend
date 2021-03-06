package com.verwaltungsplatform.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class IllnessDto {

	
	@NotNull
	@NotEmpty
	private int affectedUserId;
	
	private int sicknessNoteId;
	
	private String firstName;
   
	private String lastName;
   
    private String rolle;
    
    private boolean confirmation;

   
	// Default constructor
	public IllnessDto() {
		
	}
	
	
	public IllnessDto(int affectedUserId) {
		super();
		this.affectedUserId = affectedUserId;
		this.firstName = "";
		this.lastName = "";
		this.rolle = "";
	}
	


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAffectedUserId() {
		return affectedUserId;
	}
	public void setAffectedUserId(int affectedUserId) {
		this.affectedUserId = affectedUserId;
	}


	public String getRolle() {
		return rolle;
	}


	public void setRolle(String rolle) {
		this.rolle = rolle;
	}


	public int getSicknessNoteId() {
		return sicknessNoteId;
	}


	public void setSicknessNoteId(int sicknessNoteId) {
		this.sicknessNoteId = sicknessNoteId;
	}


	public boolean getConfirmation() {
		return confirmation;
	}


	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}


	
}
