package com.wipro.doconnect.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.doconnect.dto.ResponseDTO;
import com.wipro.doconnect.entity.Admin;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exception.AlreadyThere;
import com.wipro.doconnect.exception.NotFound;
import com.wipro.doconnect.repository.IAdminRepo;
import com.wipro.doconnect.repository.IAnswerRepo;
import com.wipro.doconnect.repository.IQuestionRepo;
import com.wipro.doconnect.repository.IUserRepo;
import com.wipro.doconnect.util.EmailSenderService;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private IAdminRepo adminRepo;

	@Autowired
	private IQuestionRepo questionRepo;

	@Autowired
	private IAnswerRepo answerRepo;

	@Autowired
	private IUserRepo userRepo;

	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public Admin adminLogin(String email, String password) {

		Admin admin = adminRepo.findByEmail(email);
		if (Objects.isNull(admin))
			throw new NotFound();

		if (admin.getPassword().equals(password)) {
			admin.setIsActive(true);
			adminRepo.save(admin);
		} else
			throw new NotFound();
		return admin;
	}

	@Override
	public String adminLogout(Long adminId) {

		Admin admin = adminRepo.findById(adminId).orElseThrow(() -> new NotFound("Admin not found"));
		admin.setIsActive(false);
		adminRepo.save(admin);
		return "Logged Out";
	}

	@Override
	public Admin adminRegister(Admin admin) {

		Admin admin1 = adminRepo.findByEmail(admin.getEmail());
		Admin last_rec = adminRepo.findTopByOrderByIdDesc();
		admin.setId(last_rec.getId()+1);
		if (Objects.isNull(admin1))
			
			return adminRepo.save(admin);
			
		throw new AlreadyThere();
	}

	@Override
	public List<Question> getUnApprovedQuestions() {
		return questionRepo.findByIsApproved();
	}

	@Override
	public List<Answer> getUnApprovedAnswers() {
		return answerRepo.findByIsApproved();
	}

	@Override
	public Question approveQuestion(Long questionId) {

		Question question = questionRepo.findById(questionId).orElseThrow(() -> new NotFound("Question not found"));

		question.setIsApproved(true);
		question = questionRepo.save(question);

		List<Admin> admins = adminRepo.findAll();
		for (Admin admin : admins) {
			sendMail(admin.getEmail(), "A new question has been added to the database. \n Check it out here: http://localhost:4200/admin");
		}
		// a mail should go to the list of admins that the question is approved

		return question;
	}

	@Override
	public Answer approveAnswer(Long answerId) {
		Answer answer = answerRepo.findById(answerId).orElseThrow(() -> new NotFound("Answer not found"));

		answer.setIsApproved(true);
		answer = answerRepo.save(answer);

		List<Admin> admins = adminRepo.findAll();
			for (Admin admin : admins) {
			sendMail(admin.getEmail(), "A new answer has been added to the database. \n Check it out here: ttp://localhost:4200/admin");
		}

		// a mail should go to the admin that a answer is published
		return answer;
	}

	@Override
	public ResponseDTO deleteQuestion(Long questionId) {

		ResponseDTO responseDTO = new ResponseDTO();
		Question question = questionRepo.findById(questionId).orElseThrow(() -> new NotFound("Question not found"));

		questionRepo.delete(question);
		responseDTO.setMsg("Question removed");
		return responseDTO;
	}

	@Override
	public ResponseDTO deleteAnswer(Long answerId) {
		ResponseDTO responseDTO = new ResponseDTO();

		Answer answer = answerRepo.findById(answerId).orElseThrow(() -> new NotFound("Answer not found"));

		answerRepo.delete(answer);
		responseDTO.setMsg("Answer Removed");
		return responseDTO;
	}

	public Boolean sendMail(String emailId, String type) {
		try {
			emailSenderService.sendEmail(emailId, type, type);
			return true;
		} catch (Exception e) {
			System.out.println("error in sending mail " + e);
			return false;
		}
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

}
