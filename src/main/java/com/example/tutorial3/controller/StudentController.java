package com.example.tutorial3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;
import com.example.tutorial3.model.StudentModel;
import java.util.List;


@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm,
					@RequestParam(value = "name", required = true) String name,
					@RequestParam(value = "gpa", required = true) double gpa) {
		StudentModel student = new StudentModel(npm, name, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
	@RequestMapping("/student/viewall")
	public String viewAll(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
	
	@RequestMapping("/student/view/{npm}")
	public String viewPath(@PathVariable String npm, Model model) {
		StudentModel student = studentService.selectStudent(npm);
		if(student == null) {
			return "errorpage";
		} else {
			model.addAttribute("student", student);
			return "view";
		}
	}
	
	@RequestMapping("/student/view")
	public String viewKosong(Model model) {
		return "errorpage";
	}
	
	@RequestMapping("/student/delete/{npm}")
	public String delete(@PathVariable String npm, Model model) {
		StudentModel student = studentService.selectStudent(npm);
		if(student == null) {
			return "errorpage";
		} else {
			studentService.deleteStudent(npm);
			return "delete";
		}
	}
	
	@RequestMapping("/student/delete")
	public String deleteKosong(Model model) {
		return "errorpage";
	}
}
