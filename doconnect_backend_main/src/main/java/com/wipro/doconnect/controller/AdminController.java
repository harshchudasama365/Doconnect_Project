package com.wipro.doconnect.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.doconnect.dto.ResponseDTO;
import com.wipro.doconnect.entity.Admin;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.service.IAdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

	@Autowired
	private IAdminService adminService;

	@GetMapping("/login/{email}/{password}")
	public Admin adminLogin(@PathVariable String email, @PathVariable String password) {
		return adminService.adminLogin(email, password);
	}
	// change the resonse to admin or user

	@GetMapping("/logout/{adminId}")
	public String adminLogout(@PathVariable Long adminId) {
		return adminService.adminLogout(adminId);
	}

	@PostMapping("/register")
	public Admin adminRegister(@Valid @RequestBody Admin admin) {
		return adminService.adminRegister(admin);
	}

	@GetMapping("/getUnApprovedQuestions")
	public List<Question> getUnApprovedQuestions() {
		return adminService.getUnApprovedQuestions();
	}

	@GetMapping("/getUnApprovedAnswers")
	public List<Answer> getUnApprovedAnswers() {
		return adminService.getUnApprovedAnswers();
	}

	@PutMapping("/approveQuestion/{questionId}")
	public Question approveQuestion(@PathVariable Long questionId) {
		return adminService.approveQuestion(questionId);
	}

	@PutMapping("/approveAnswer/{answerId}")
	public Answer approveAnswer(@PathVariable Long answerId) {
		return adminService.approveAnswer(answerId);
	}

	@DeleteMapping("/deleteQuestion/{questionId}")
	public ResponseDTO deleteQuestion(@PathVariable Long questionId) {
		return adminService.deleteQuestion(questionId);
	}

	@DeleteMapping("/deleteAnswer/{answerId}")
	public ResponseDTO deleteAnswer(@PathVariable Long answerId) {
		return adminService.deleteAnswer(answerId);
	}

	@GetMapping("/getUser/{email}")
	public User getUser(@PathVariable String email) {
		return adminService.getUser(email);
	}

	@GetMapping("/getAllUsers")
	public List<User> getAllUser() {
		return adminService.getAllUser();
	}

}
