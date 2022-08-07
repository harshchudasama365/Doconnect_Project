import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user';
import { Observable } from 'rxjs';
import { Question } from './question';
import { Answer } from './answer';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) {

   }

questionId:number=0
   user = new User()
  url:string="http://localhost:9090/user"

  sendUserData(user:User){
    this.user.id=user.id
	this.user.name=user.name
  this.user.email=user.email
  }

  giveUserData(){
    return this.user
   }

   getQuestionId(questionId:number){
    this.questionId=questionId
   }
   sendQuestionId(){
    return this.questionId

   }

	userLogin(email:string, password:string):Observable<User> {
		return this.http.get<User>(this.url+"/login/"+email+"/"+password);
	}

	userLogout( userId:number) {
		return this.http.get(this.url+"/logout/"+userId);
	}

	userRegister(user:User):Observable<User> {
		return this.http.post<User>(this.url+"/register",user)
	}

  askQuestion(askQuestionDTO:any):Observable<Question> {
	  return this.http.post<Question>(this.url+"/askQuestion",askQuestionDTO);
	}

  giveAnswer(postAnswerDTO:any):Observable<Answer> {
	  return this.http.post<Answer>(this.url+"/giveAnswer",postAnswerDTO);
	}

	searchQuestion(question:string):Observable<Question[]> {
	  return this.http.get<Question[]>(this.url+"/searchQuestion/"+question);
	}

  getAnswers(questionId:number):Observable<Answer[]> {
		return this.http.get<Answer[]>(this.url+"/getAnswers/"+questionId);
	}

	getQuestions(topic:string):Observable<Question[]> {
		return this.http.get<Question[]>(this.url+"/getQuestions/"+topic);
	}



}
