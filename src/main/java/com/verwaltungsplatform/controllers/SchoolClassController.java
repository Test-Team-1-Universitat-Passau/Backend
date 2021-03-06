package com.verwaltungsplatform.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.verwaltungsplatform.dto.IllnessConfirmationDto;
import com.verwaltungsplatform.dto.PresenceDto;
import com.verwaltungsplatform.model.SchoolClass;
import com.verwaltungsplatform.repositories.SchoolClassRepository;
import com.verwaltungsplatform.service.PresenceService;
import com.verwaltungsplatform.service.SchoolClassService;

@Controller
public class SchoolClassController {
	
	@Autowired
	SchoolClassService schoolClassService;
	
	@Autowired
	SchoolClassRepository schoolClassRepo;
	
	@Autowired
	PresenceService presenceService;
	
	
	
	// Das  Sekretariat  kann neue  Lernende  anlegen  und  Ihnen  dabei  eine Klasse zuweisen
	// Das  Sekretariat kann Klassen  von  vorhandenen  Lernenden  ändern
	// „Lehrende“ können Klassen  einsehen
	// Wenn „Lehrende“ sich eine Klasse anzeigen lassen, können  sie  für  jeden Lernenden  dieser  Klasse  für  ihre  Unterrichtsstundedie An-bzw.  Abwesenheit jedes   individuellen   Lernendeneintragen
	// Wenn eine Krankmeldung  eines  „Lernenden“ durch das Sekretariat bestätigt ist, ist dies ersichtlich für Lehrende 

	
	
	// Das Sekretariat erhält eine Liste aller Schüler einer bestimmten Klasse
	// Ausgabe UserDto:	userId, Vorname, Nachname, Email, zugehörige Klasse
//	@GetMapping("/sekretariat/klassenliste/{classId}")
//	@ResponseBody
//	public List<UserDto> getStudentsClass(@PathVariable("classId") String classId) {
//		
//		List<UserDto> classmemberList = schoolClassService.getClassMember(String classId); 	
//		// Methode getClassMember(String classId) gibt alle Schüler einer bestimmten Klasse aus. 
//		
//		return classmemberList;
//	}
	
	// Das Sekretariat erhält eine Liste aller Schüler, die noch keiner Klasse zugewiesen wurden
//	@GetMapping("/sekretariat/klassenliste/keineklasse")
//	@ResponseBody
//	public List<UserDto> getStudentsClassNULL() {
//		
//		List<UserDto> studentsWithoutClass = schoolClassService.getClassMemberNULL();
//		// Methode getClassMemberNULL() gibt alle Schüler aus, die noch keiner keiner Klasse zugewiesen wurden
//		
//		return studentsWithoutClass;
//	}
	
	// Fügt einen Schüler einer bestimmten Klasse hinzu. Der Schüler war zuvor keiner Klasse zugeordnet.
	@PostMapping("/sekretariat/klassenliste/keineklasse/klassehinzufuegen")
	@ResponseBody
	public String addStudentToClass(int studentId, String classId) {
		
		SchoolClass allocation = new SchoolClass(classId, studentId);
		schoolClassRepo.save(allocation);
		
		String response = "Der Schüler wurde der Klasse " + classId + " zugeordnet.";
		return response;
	}
	
	// Ändert die Klasse eines Schülers. Der Schüler war zuvor bereits einer Klasse zugeordnet.
	@PutMapping("/sekretariat/klassenliste/klasseaendern")
	@ResponseBody
	public String changeClass(int studentId, String classId) {
		
		SchoolClass allocation = schoolClassRepo.getOne(studentId);
		allocation.setName(classId);
		schoolClassRepo.save(allocation);
		
		String response = "Der Schüler wurde der Klasse " + classId + " zugeordnet.";
		return response;
	}
	
	// Gibt einem Lehrenden die Klassenliste einer bestimmten Klasse aus
	// Fehler, wenn für einen Schüler der Klasse keine Krankmeldung existiert
	// IllnessConfirmationDto benötigt noch die Attribute: boolean presence (standardmäßig true), String farbe
	// Könnte vielleicht auch alternativ als List<PresenceDto> gelöst werden 
	@GetMapping("/lehrender/klassenliste/{classId}")
	@ResponseBody
	public List<IllnessConfirmationDto> getClassList(@PathVariable("classId") String classId) {
		
		List<IllnessConfirmationDto> classList = schoolClassService.getAllIllnessConfirmation(classId);
		
		return classList;
	}
	
	// Erstellt einen neuen Anwesenheitseintrag für einen bestimmten Schüler
	@PostMapping("/lehrender/klassenliste/abwesenheiteintragen")
	@ResponseBody
	public PresenceDto addAbsence(int affectedUserId, String date, int lesson, boolean presence,
			boolean confirmation) {
		
		PresenceDto newAbsence = new PresenceDto(affectedUserId, date, lesson, presence, confirmation);
		presenceService.savePresenceEntry(newAbsence);
		
		return newAbsence;
	}

}
