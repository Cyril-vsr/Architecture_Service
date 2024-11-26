package fr.insa.mas.studentManagementMS.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import fr.insa.mas.studentManagementMS.model.Student;

@RestController
public class studentResources {
	@GetMapping("/students")
	public int studentNumber() {
		return 200;
	}
	@GetMapping(value="/students/{id}")
	public Student infosStudent(@PathVariable int id) {
		Student student=new Student (id, "Paul", "Cyril");
		return student;
	}
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_XML_VALUE)
	public Student xmlInfosStudent(@PathVariable int id) {
		Student student=new Student (id, "Paul", "Cyril");
		return student;
	}
	@PostMapping("/addStudent") 
	public void addEtudiant(@RequestBody Student student) {
		student.getId();
		student.getLastName();
		student.getFirstName();
	}
	
}
