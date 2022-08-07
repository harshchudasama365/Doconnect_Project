package com.wipro.doconnect.service;

import java.util.List;

import com.wipro.doconnect.dto.ResponseDTO;
import com.wipro.doconnect.entity.Admin;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.entity.User;

public interface IAdminService {

	public Admin adminLogin(String email, String password);

	public String adminLogout(Long adminId);

	public Admin adminRegister(Admin admin);

	public List<Question> getUnApprovedQuestions();

	public List<Answer> getUnApprovedAnswers();

	public Question approveQuestion(Long questionId);

	public Answer approveAnswer(Long answerId);

	public ResponseDTO deleteQuestion(Long questionId);

	public ResponseDTO deleteAnswer(Long answerId);

	public User getUser(String email);

	public List<User> getAllUser();

}
