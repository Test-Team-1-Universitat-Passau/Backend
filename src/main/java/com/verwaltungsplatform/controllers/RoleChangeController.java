package com.verwaltungsplatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.verwaltungsplatform.model.Family;
import com.verwaltungsplatform.model.Role_RegisterCode_Mapper;
import com.verwaltungsplatform.model.SchoolClass;
import com.verwaltungsplatform.model.User;
import com.verwaltungsplatform.repositories.FamilyRepository;
import com.verwaltungsplatform.repositories.SchoolClassRepository;
import com.verwaltungsplatform.repositories.UserRepository;

import java.util.Map;

@Controller
public class RoleChangeController {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private FamilyRepository familyRepo;
	@Autowired
	private SchoolClassRepository schoolClassRepo;
	
	// Das Sekretariat kann einem bestimmten Nutzer die Rollen "Lernender" und "Eltern" zuweisen
	@PutMapping("/sekretariat/changerole")
	@ResponseBody
	public String changeRoleSekretariat(String eMail, String newRole) {
		
		User user = userRepo.findByEmail(eMail);
		String response;
		
		if(user == null) {
			response = "Es konnte kein Nutzer mit dieser E-Mail Adresse gefunden werden.";
			return response;
		}
		else {
			if(user.getRoleRegisterCodeMapper().getRole() == "Admin") {
				response = "Die Rolle des Nutzers konnte nicht geändert werden, da der Nutzer ein Administrator ist.";
				return response;
			}
			else if(user.getRoleRegisterCodeMapper().getRole() == "Sekretariat") {
				response = "Die Rolle des Nutzers konnte nicht geändert werden, da der Nutzer ein Mitglied des Sekretariats ist.";
				return response;
			}
			else if(user.getRoleRegisterCodeMapper().getRole() == "Lehrender") {
				response = "Die Rolle des Nutzers konnte nicht geändert werden, da der Nutzer ein Lehrender ist.";
				return response;
			}
			else {
				Role_RegisterCode_Mapper userRole = new Role_RegisterCode_Mapper(newRole);
				user.setRoleRegisterCodeMapper(userRole);
				userRepo.save(user);
				response = "Die Rolle des Nutzers wurde in " + newRole + " geändert.";
				return response;
			}
		}
	}
	
	// Ein Administrator kann einem bestimmten Nutzer jede Rolle zuweisen
	@PutMapping("/admin/changerole")
	@ResponseBody
	public String changeRoleAdmin(@RequestBody Map<String,String> rolechange) {
		System.out.print(rolechange.get("eMail"));
		User user = userRepo.findByEmail(rolechange.get("eMail"));
		String response;
		
		if(user == null) {
			response = "Es konnte kein Nutzer mit dieser E-Mail Adresse gefunden werden.";
			return response;
		}
		else {
			Role_RegisterCode_Mapper userRole = new Role_RegisterCode_Mapper(rolechange.get("newRole"));
			user.setRoleRegisterCodeMapper(userRole);
			userRepo.save(user);
			response = "Die Rolle des Nutzers wurde in " + (rolechange.get("newRole")) + " geändert.";
			return response;
		}
	}
	
	
	
	// Das Sekretariat kann einen neuen Lernenden anlegen
		@PostMapping("/sekretariat/neuerlernender")
		@ResponseBody
		public String addStudent(@RequestBody Map<String,String> newstudent) {
			
			Role_RegisterCode_Mapper role = new Role_RegisterCode_Mapper("Lernender");
			User newStudent = new User(newstudent.get("firstName"), newstudent.get("lastName"), newstudent.get("email"), newstudent.get("password"), role);
			userRepo.save(newStudent);

			SchoolClass allocation = new SchoolClass(newstudent.get("classId"), newStudent.getId());
			schoolClassRepo.save(allocation);
			int familyId=(familyRepo.getMaxFamilyId()+1);
			Family newFamily = new Family(newStudent.getId(), familyId);
			familyRepo.save(newFamily);

			String response = "Der Lernende wurde im System hinzugefügt. \nVorname:	" + newstudent.get("firstName")
					+ "\nNachname: 	" + newstudent.get("lastName")
					+ "\nE-Mail: 	" + newstudent.get("email")
					+ "\nPasswort: 	" + newstudent.get("password")
					+ "\nKlasse: 	" + newstudent.get("classId")
					+ "\nFamilie: 	" + newFamily.getFamilyId();
			
			return response;
			
			
		}

}
